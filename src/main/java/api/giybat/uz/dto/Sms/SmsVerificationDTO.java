package api.giybat.uz.dto.Sms;

import jakarta.validation.constraints.NotBlank;

public class SmsVerificationDTO {

    @NotBlank(message = "Phone is required")
    private String Phone;
    @NotBlank(message = "Code is required")
    private String Code;

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
