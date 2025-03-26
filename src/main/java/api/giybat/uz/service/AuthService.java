package api.giybat.uz.service;

import api.giybat.uz.dto.*;
import api.giybat.uz.dto.Auth.RegistrationDTO;
import api.giybat.uz.dto.Auth.ResetPasswordDTO;
import api.giybat.uz.dto.Auth.ResetPasswordVerDTO;
import api.giybat.uz.dto.Sms.ReSendSmsDTO;
import api.giybat.uz.dto.Sms.SmsVerificationDTO;
import api.giybat.uz.entity.ProfileEntity;
import api.giybat.uz.enums.AppLanguage;
import api.giybat.uz.enums.GeneralStatus;
import api.giybat.uz.enums.ProfileRoles;
import api.giybat.uz.exps.AppBadExseption;
import api.giybat.uz.repozitory.ProfileRepository;
import api.giybat.uz.util.EmailUtil;
import api.giybat.uz.util.JwtUtil;
import api.giybat.uz.util.PhoneUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ProfileRoleService profileRoleService;

    @Autowired
    private EmailSendingService emailSendingService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private LanguageMessageService languageMessage;

    @Autowired
    private SmsSendService smsSendService;

    @Autowired
    private SmsHistoryService smsHistoryService;

    @Autowired
    private AttachService attachService;


    public AppResponse<String> registr(RegistrationDTO registrationDTO, AppLanguage lang) {
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(registrationDTO.getUsername());
        if (optional.isPresent()) {
            ProfileEntity profileEntity = optional.get();
            if (profileEntity.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
                // send sms
                profileRepository.updateNameById(profileEntity.getId(), registrationDTO.getName());
                profileRepository.updatePasswordById(profileEntity.getId(), bCryptPasswordEncoder.encode(registrationDTO.getPassword()));
                if (PhoneUtil.isPhone(registrationDTO.getUsername()))
                {
                    smsSendService.sendRegistrationSms(registrationDTO.getUsername());
                }
                if (EmailUtil.isEmail(registrationDTO.getUsername()))
                {
                    emailSendingService.sendRegistrationEmail(registrationDTO.getUsername(),profileEntity.getId(),lang);
                    return new AppResponse<>(languageMessage.getMessage("email.conform.resend", lang));
                }

            } else {
                throw new AppBadExseption(languageMessage.getMessage("email.phone.exist", lang));
            }

        }

        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setName(registrationDTO.getName());
        profileEntity.setUsername(registrationDTO.getUsername());
        profileEntity.setPassword(bCryptPasswordEncoder.encode(registrationDTO.getPassword())); // password encoder
        profileEntity.setStatus(GeneralStatus.IN_REGISTRATION);
        profileEntity.setVisible(true);
        profileEntity.setCreatedDate(LocalDate.now());
        profileEntity.setUpdatedDate(LocalDate.now());
        profileRepository.save(profileEntity); //save
        //Insert role
        profileRoleService.createProfileRole(profileEntity.getId(), ProfileRoles.ROLE_USER);

        //send email or phone sms send
        if (EmailUtil.isEmail(registrationDTO.getUsername()))
        {
            emailSendingService.sendRegistrationEmail(registrationDTO.getUsername(),profileEntity.getId(),lang);
            return new AppResponse<>(languageMessage.getMessage("email.conform.send", lang));

        } else if (PhoneUtil.isPhone(registrationDTO.getUsername()))
        {
            smsSendService.sendRegistrationSms(registrationDTO.getUsername());
        }
        return new AppResponse<>(languageMessage.getMessage("verification.failed", lang));
    }

    public AppResponse<String> RegistrationEmailVerification(String token, AppLanguage lang) {
        try {
            Long profileId = JwtUtil.decodeRegVerToken(token);
            ProfileEntity profileEntity = profileService.getProfileById(profileId);
            if (profileEntity.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
                profileRepository.changeStatusById(profileId, GeneralStatus.ACTIVE);
                return new AppResponse<>(languageMessage.getMessage("profile.verify", lang));

            }
        } catch (Exception e) {
        }

        throw new AppBadExseption(languageMessage.getMessage("verification.failed", lang));
    }

    public ProfileDTO login(AuthDTO authDTO, AppLanguage lang) {
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(authDTO.getUsername());
        if (optional.isEmpty()) {
            throw new AppBadExseption(languageMessage.getMessage("logins.error", lang));
        }
        if (!bCryptPasswordEncoder.matches(authDTO.getPassword(), optional.get().getPassword())) {
            throw new AppBadExseption(languageMessage.getMessage("logins.error", lang));
        }
        if (!optional.get().getStatus().equals(GeneralStatus.ACTIVE)) {
            throw new AppBadExseption(languageMessage.getMessage("logins.statuscheck", lang));
        }
        //response
        ProfileDTO response = new ProfileDTO();
        response.setName(optional.get().getName());
        response.setUsername(optional.get().getUsername());
        response.setRoles(profileRoleService.getProfileRoles(optional.get().getId()));
        response.setToken(JwtUtil.encode(optional.get().getUsername(), optional.get().getId(), profileRoleService.getProfileRoles(optional.get().getId())));
        response.setPhoto(attachService.attachDTO(optional.get().getPhotoId()));


        return response;

    }

    public ProfileDTO RegistrationSmsVerification(SmsVerificationDTO smsVerificationDTO, AppLanguage lang) {

        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(smsVerificationDTO.getPhone());
        if (optional.isEmpty()) {
            throw new AppBadExseption(languageMessage.getMessage("phone.not.exist", lang));
        }
        ProfileEntity profileEntity = optional.get();
        if (!profileEntity.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
            throw new AppBadExseption(languageMessage.getMessage("phone.exist", lang));
        }
        // xaqiqiy smsni tekshirish
        //smsHistoryService.check(smsVerificationDTO.getPhone(), smsVerificationDTO.getCode(), lang);
        // default holat uchun
        if (!smsVerificationDTO.getCode().equals("12345")) {
            throw new AppBadExseption(languageMessage.getMessage("sms.code.error", lang));
        }
        profileRepository.changeStatusById(profileEntity.getId(), GeneralStatus.ACTIVE);
        ProfileDTO response = new ProfileDTO();
        response.setName(optional.get().getName());
        response.setUsername(optional.get().getUsername());
        response.setRoles(profileRoleService.getProfileRoles(optional.get().getId()));
        response.setToken(JwtUtil.encode(optional.get().getUsername(), optional.get().getId(), profileRoleService.getProfileRoles(optional.get().getId())));


        return response;
    }

    public AppResponse<String> resendSms(ReSendSmsDTO reSendSmsDTO, AppLanguage lang) {
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(reSendSmsDTO.getPhone());
        if (optional.isEmpty()) {
            throw new AppBadExseption(languageMessage.getMessage("phone.not.exist", lang));
        }
        ProfileEntity profileEntity = optional.get();
        if (!profileEntity.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
            throw new AppBadExseption(languageMessage.getMessage("phone.exist", lang));
        }
        // Smslarni qayta yuborish soni 3 dan oshib ketmasligini tekshirish
        Long count = smsHistoryService.getSmsCount(reSendSmsDTO.getPhone());
        Long smsLimit = 3L;
        if (smsLimit <=count)
        {
            throw new AppBadExseption("Sms limit reached");
        }
        smsSendService.sendRegistrationSms(reSendSmsDTO.getPhone());
        return new AppResponse<>(languageMessage.getMessage("sms.resend", lang));
    }

    public AppResponse<String> resetPassword(ResetPasswordDTO resetPasswordDTO,AppLanguage lang){
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(resetPasswordDTO.getUsername());
        if (optional.isEmpty()) {
            throw new AppBadExseption(languageMessage.getMessage("phone.not.exist", lang));
        }
        ProfileEntity profileEntity = optional.get();
        if (!profileEntity.getStatus().equals(GeneralStatus.ACTIVE)) {
            throw new AppBadExseption(languageMessage.getMessage("logins.statuscheck", lang));
        }

        if (EmailUtil.isEmail(resetPasswordDTO.getUsername()))
        {
            emailSendingService.resetPasswordEmail(resetPasswordDTO.getUsername(),lang);
            return new AppResponse<>(languageMessage.getMessage("email.resetpassword.code", lang));

        } else if (PhoneUtil.isPhone(resetPasswordDTO.getUsername()))
        {
            smsSendService.sendResetPasswordSms(resetPasswordDTO.getUsername());
        }

        return new AppResponse<>(languageMessage.getMessage("sms.reset.password", lang));
    }

    public AppResponse<String> resetPasswordChange(ResetPasswordVerDTO resDTO, AppLanguage lang) {
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(resDTO.getUsername());
        if (optional.isEmpty()) {
            throw new AppBadExseption(languageMessage.getMessage("phone.not.exist", lang));
        }
        ProfileEntity profileEntity = optional.get();
        if (!profileEntity.getStatus().equals(GeneralStatus.ACTIVE)) {
            throw new AppBadExseption(languageMessage.getMessage("phone.exist", lang));
        }
        if (!smsHistoryService.check(resDTO.getUsername(),resDTO.getVerifcationCode(),lang)){
            throw new AppBadExseption(languageMessage.getMessage("phone.exist", lang));
        }

        profileRepository.updatePasswordById(profileEntity.getId(),bCryptPasswordEncoder.encode(resDTO.getNewPassword()));

        return new AppResponse<>(languageMessage.getMessage("password.update", lang));

    }



}
