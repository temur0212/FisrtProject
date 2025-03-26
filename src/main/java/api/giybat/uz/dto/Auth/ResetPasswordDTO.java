package api.giybat.uz.dto.Auth;

import jakarta.validation.constraints.NotBlank;

public class ResetPasswordDTO {
    @NotBlank(message = "Username required")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
