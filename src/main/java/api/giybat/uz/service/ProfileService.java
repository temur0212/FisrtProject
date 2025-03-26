package api.giybat.uz.service;

import api.giybat.uz.dto.AppResponse;
import api.giybat.uz.dto.Profile.*;
import api.giybat.uz.dto.ProfileDTO;
import api.giybat.uz.entity.ProfileEntity;
import api.giybat.uz.enums.AppLanguage;
import api.giybat.uz.enums.GeneralStatus;
import api.giybat.uz.exps.AppBadExseption;
import api.giybat.uz.repozitory.ProfileRepository;
import api.giybat.uz.util.EmailUtil;
import api.giybat.uz.util.JwtUtil;
import api.giybat.uz.util.PhoneUtil;
import api.giybat.uz.util.SpringSecurityUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private LanguageMessageService languageMessage;

    @Autowired
    private SmsSendService smsSendService;

    @Autowired
    private SmsHistoryService smsHistoryService;

    @Autowired
    private EmailSendingService emailSendingService;

    @Autowired
    private ProfileRoleService profileRoleService;

    @Autowired
    private AttachService attachService;

    public ProfileEntity getProfileById(Long id) {
        Optional<ProfileEntity> optional= profileRepository.findByIdAndVisibleTrue(id);
        if (optional.isEmpty())
        {
            throw new AppBadExseption("Profile not found");
        }
        return optional.get();
    }

    public AppResponse<String> updateName(NameDTO nameDTO, AppLanguage lang) {

        Long id = SpringSecurityUtil.getCurrentUserId();
        profileRepository.updateNameById(id,nameDTO.getName());
        return new AppResponse<>(languageMessage.getMessage("update.name",lang));
    }

    public AppResponse<String> updatePassword(PasswordDTO passwordDTO, AppLanguage lang) {
        Long id = SpringSecurityUtil.getCurrentUserId();
        ProfileEntity profileEntity = getProfileById(id);

        // Parolni tekshirish
        if (!bCryptPasswordEncoder.matches(passwordDTO.getOldPassword(), profileEntity.getPassword())) {
            throw new AppBadExseption(languageMessage.getMessage("password.error", lang));
        }

        // Yangi parolni hash qilib bazaga saqlash
        profileRepository.updatePasswordById(id, bCryptPasswordEncoder.encode(passwordDTO.getNewPassword()));

        return new AppResponse<>(languageMessage.getMessage("password.update", lang));
    }

    public AppResponse<String> updateUsername( UsernameDTO usernameDTO, AppLanguage lang) {
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(usernameDTO.getUsername());
        //check
        if (optional.isPresent()){
            throw new AppBadExseption(languageMessage.getMessage("email.phone.exist",lang));
        }

        // send sms
        if (PhoneUtil.isPhone(usernameDTO.getUsername()))
        {
            smsSendService.sendUpdateUsernameSms(usernameDTO.getUsername());
        }
        if (EmailUtil.isEmail(usernameDTO.getUsername()))
        {
            emailSendingService.UpdateUsernameEmail(usernameDTO.getUsername(),lang);
        }
        // save
        Long id = SpringSecurityUtil.getCurrentUserId();
        profileRepository.updateTempUsernameById(id,usernameDTO.getUsername());
        return new AppResponse<>(languageMessage.getMessage("email.resetpassword.code", lang));
    }

    public ProfileDTO updateUsernameVerification(@Valid UsernameVerificationDTO usernameVerificateDTO, AppLanguage lang) {
        Long id = SpringSecurityUtil.getCurrentUserId();

        Optional<ProfileEntity> optional1 = profileRepository.findByIdAndVisibleTrue(id);
        if (optional1.isEmpty()) {
            throw new AppBadExseption(languageMessage.getMessage("phone.not.exist", lang));
        }
        ProfileEntity profileEntity = optional1.get();
        if (!profileEntity.getStatus().equals(GeneralStatus.ACTIVE)) {
            throw new AppBadExseption(languageMessage.getMessage("phone.exist", lang));
        }
        if (!smsHistoryService.check(profileEntity.getTemp_username(),usernameVerificateDTO.getCode(),lang)){
            throw new AppBadExseption(languageMessage.getMessage("phone.exist", lang));
        }
        profileRepository.updateUsernameById(id,profileEntity.getTemp_username());

        ProfileDTO response = new ProfileDTO();
        response.setName(profileEntity.getName());
        response.setUsername(profileEntity.getTemp_username());
        response.setRoles(profileRoleService.getProfileRoles(id));
        response.setToken(JwtUtil.encode(profileEntity.getTemp_username(), id, profileRoleService.getProfileRoles(id)));

        return response;
//        return new AppResponse<>(languageMessage.getMessage("update.name", lang));
    }

    public AppResponse<String> updatePhoto(@Valid String attachId, AppLanguage lang) {
        Long id = SpringSecurityUtil.getCurrentUserId();
        ProfileEntity profile = getProfileById(id);
        profileRepository.updatePhotoById(id,attachId);
        if (profile.getPhotoId() != null && !profile.getPhotoId().equals(attachId) ){
            attachService.delete(profile.getPhotoId());  // delete old photo
        }

        return new AppResponse<>(languageMessage.getMessage("image.update", lang));
    }
}

