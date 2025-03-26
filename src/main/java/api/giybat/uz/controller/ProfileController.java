package api.giybat.uz.controller;

import api.giybat.uz.dto.AppResponse;
import api.giybat.uz.dto.Profile.*;
import api.giybat.uz.dto.ProfileDTO;
import api.giybat.uz.enums.AppLanguage;
import api.giybat.uz.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PutMapping("/detail")
    private ResponseEntity<AppResponse<String>> updateName(@Valid @RequestBody  NameDTO nameDTO,
                                                           @RequestHeader(value = "Accept-Language",defaultValue = "EN")AppLanguage lang) {
            return ResponseEntity.ok().body(profileService.updateName(nameDTO,lang));

    }

    @PutMapping("/password")
    private ResponseEntity<AppResponse<String>> updatePassword(@Valid @RequestBody PasswordDTO passwordDTO,
                                                           @RequestHeader(value = "Accept-Language",defaultValue = "EN")AppLanguage lang) {
        return ResponseEntity.ok().body(profileService.updatePassword(passwordDTO,lang));

    }

    @PutMapping("/photo")
    private ResponseEntity<AppResponse<String>> updatePhoto(@Valid @RequestBody ImageUpdateDTO imageUpdateDTO,
                                                           @RequestHeader(value = "Accept-Language",defaultValue = "EN")AppLanguage lang) {
        return ResponseEntity.ok().body(profileService.updatePhoto(imageUpdateDTO.getAttachId(),lang));

    }

    @PutMapping("/username")
    private ResponseEntity<AppResponse<String>> updateUsername(@Valid @RequestBody UsernameDTO usernameDTO,
                                                               @RequestHeader(value = "Accept-Language",defaultValue = "EN")AppLanguage lang) {
        return ResponseEntity.ok().body(profileService.updateUsername(usernameDTO,lang));

    }
    @PutMapping("/username/confirm")
    private ResponseEntity<ProfileDTO> updateUsernameConfirm(@Valid @RequestBody UsernameVerificationDTO usernameVerificateDTO,
                                                             @RequestHeader(value = "Accept-Language",defaultValue = "EN")AppLanguage lang) {
        return ResponseEntity.ok().body(profileService.updateUsernameVerification(usernameVerificateDTO,lang));

    }
}
