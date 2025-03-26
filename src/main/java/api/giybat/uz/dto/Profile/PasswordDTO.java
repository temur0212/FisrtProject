package api.giybat.uz.dto.Profile;

import jakarta.validation.constraints.NotBlank;

public class PasswordDTO {
    @NotBlank(message = "Password required")
    private String oldPassword;
    @NotBlank(message = "Password required")
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
