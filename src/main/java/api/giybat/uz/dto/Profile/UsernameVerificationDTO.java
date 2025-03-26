package api.giybat.uz.dto.Profile;

import jakarta.validation.constraints.NotBlank;

public class UsernameVerificationDTO {
    @NotBlank(message = "Code required")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
