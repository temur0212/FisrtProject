package api.giybat.uz.dto;

import jakarta.validation.constraints.NotNull;

public class AttachCreateDTO {
    @NotNull(message = "Id must not be empty")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
