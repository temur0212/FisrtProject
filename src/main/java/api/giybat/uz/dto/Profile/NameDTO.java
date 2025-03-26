package api.giybat.uz.dto.Profile;

import jakarta.validation.constraints.NotBlank;

public class NameDTO {
    @NotBlank(message = "Name required")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
