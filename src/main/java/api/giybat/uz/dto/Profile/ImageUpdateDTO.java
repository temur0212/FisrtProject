package api.giybat.uz.dto.Profile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public class ImageUpdateDTO {
    @NotBlank(message = "Photo requred")
    private String attachId;

    public String getAttachId() {
        return attachId;
    }

    public void setAttachId(String attachId) {
        this.attachId = attachId;
    }
}
