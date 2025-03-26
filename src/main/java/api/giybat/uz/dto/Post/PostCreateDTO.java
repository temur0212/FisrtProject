package api.giybat.uz.dto.Post;

import api.giybat.uz.dto.AttachDTO;
import api.giybat.uz.entity.AttachEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public class PostCreateDTO {
    @NotBlank(message = "Title must not be empty")
    @Length(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    private String title;
    @NotBlank(message = "Content must not be empty")
    private String content;
    @NotNull(message = "Photo must not be empty")
    private AttachDTO photo;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AttachDTO getPhoto() {
        return photo;
    }

    public void setPhoto(AttachDTO photo) {
        this.photo = photo;
    }
}
