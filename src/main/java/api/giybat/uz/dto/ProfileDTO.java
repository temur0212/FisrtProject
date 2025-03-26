package api.giybat.uz.dto;

import api.giybat.uz.enums.ProfileRoles;

import java.util.List;

public class ProfileDTO {
    private String name;
    private String username;
    private List<ProfileRoles> roles;
    private String token;
    private AttachDTO photo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<ProfileRoles> getRoles() {
        return roles;
    }

    public void setRoles(List<ProfileRoles> roles) {
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AttachDTO getPhoto() {
        return photo;
    }

    public void setPhoto(AttachDTO photo) {
        this.photo = photo;
    }
}
