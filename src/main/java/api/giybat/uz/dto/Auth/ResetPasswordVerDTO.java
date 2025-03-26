package api.giybat.uz.dto.Auth;

import jakarta.validation.constraints.NotBlank;

public class ResetPasswordVerDTO {
    private String username;
    @NotBlank(message = "Code required")
    private String VerifcationCode;
    @NotBlank(message = "Code required")
    private String NewPassword;

    public String getVerifcationCode() {
        return VerifcationCode;
    }

    public void setVerifcationCode(String verifcationCode) {
        VerifcationCode = verifcationCode;
    }

    public String getNewPassword() {
        return NewPassword;
    }

    public void setNewPassword(String newPassword) {
        NewPassword = newPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
