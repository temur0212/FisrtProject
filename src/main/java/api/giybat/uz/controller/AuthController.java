package api.giybat.uz.controller;

import api.giybat.uz.dto.*;
import api.giybat.uz.dto.Auth.ResetPasswordDTO;
import api.giybat.uz.dto.Auth.ResetPasswordVerDTO;
import api.giybat.uz.dto.Sms.ReSendSmsDTO;
import api.giybat.uz.dto.Auth.RegistrationDTO;

import api.giybat.uz.dto.Sms.SmsVerificationDTO;
import api.giybat.uz.enums.AppLanguage;
import api.giybat.uz.service.AuthService;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthService authService;

     @PostMapping("/registration")
    private ResponseEntity<AppResponse<String>> registration(@Valid @RequestBody RegistrationDTO registrationDTO,
                                                             @RequestHeader(value = "Accept-Language",defaultValue = "EN")AppLanguage lang){
       logger.info("registration : {}",registrationDTO);
        return ResponseEntity.ok().body(authService.registr(registrationDTO,lang));
    }


    @GetMapping("/registration/email-verification/{language}/{token}")
    private ResponseEntity<AppResponse<String>> EmailVerification(@PathVariable("token") String profileId,
                                                             @PathVariable("language") AppLanguage lang){
        logger.info("EmailVerification : {}",profileId);
         return ResponseEntity.ok().body(authService.RegistrationEmailVerification(profileId,lang));
    }

    @PostMapping("/registration/sms-verification")
    private ResponseEntity<ProfileDTO> SmsVerification(@Valid @RequestBody SmsVerificationDTO smsVerificationDTO,
                                                       @RequestHeader(value = "Accept-Language",defaultValue = "EN")AppLanguage lang){
        logger.info("SmsVerification : {}",smsVerificationDTO);
         return ResponseEntity.ok().body(authService.RegistrationSmsVerification(smsVerificationDTO,lang));
    }

    @PostMapping("/registration/resend-sms")
    private ResponseEntity<AppResponse<String>> SmsResend(@RequestBody ReSendSmsDTO reSendSmsDTO,
                                                          @RequestHeader(value = "Accept-Language",defaultValue = "EN")AppLanguage lang){
       logger.info("SmsResend : {}",reSendSmsDTO);
        return ResponseEntity.ok().body(authService.resendSms(reSendSmsDTO,lang));
    }


    @PostMapping("/login")
    private ResponseEntity<ProfileDTO> Login(@Valid @RequestBody AuthDTO authDTO,
                                             @RequestHeader(value = "Accept-Language",defaultValue = "EN")AppLanguage lang){
         logger.info("login : {}",authDTO);
        return ResponseEntity.ok().body(authService.login(authDTO,lang));
    }

    @PostMapping("/reset-password")
    private ResponseEntity<AppResponse<String>> ResetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO,
                                                              @RequestHeader(value = "Accept-Language",defaultValue = "EN")AppLanguage lang)
    {
    logger.info("ResetPassword : {}",resetPasswordDTO);
        return ResponseEntity.ok().body(authService.resetPassword(resetPasswordDTO,lang));
    }

    @PostMapping("/reset-password/coniform")
    private ResponseEntity<AppResponse<String>> reset_password(@Valid @RequestBody ResetPasswordVerDTO resetPasswordverDTO,
                                                               @RequestHeader(value = "Accept-Language",defaultValue = "EN")AppLanguage lang){
         logger.info("reset_password : {}",resetPasswordverDTO);
         return ResponseEntity.ok().body(authService.resetPasswordChange(resetPasswordverDTO,lang));

    }


}
