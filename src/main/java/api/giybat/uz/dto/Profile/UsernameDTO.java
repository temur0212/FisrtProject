package api.giybat.uz.dto.Profile;

import jakarta.validation.constraints.NotBlank;

public class UsernameDTO {
    @NotBlank(message = "Username required ")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
