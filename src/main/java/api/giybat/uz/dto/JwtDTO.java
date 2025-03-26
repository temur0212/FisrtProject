package api.giybat.uz.dto;

import api.giybat.uz.enums.ProfileRoles;
import lombok.AllArgsConstructor;

import java.util.List;


public class JwtDTO {
private Long id;
private String username;
private List<ProfileRoles> roles;

    public JwtDTO(Long id, String username, List<ProfileRoles> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ProfileRoles> getRoles() {
        return roles;
    }

    public void setRoles(List<ProfileRoles> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
