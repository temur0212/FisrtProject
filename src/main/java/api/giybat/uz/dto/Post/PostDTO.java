package api.giybat.uz.dto.Post;

import api.giybat.uz.dto.AttachCreateDTO;
import api.giybat.uz.dto.AttachDTO;
import api.giybat.uz.entity.AttachEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDTO {

    private String id;
    private String title;
    private String content;
    private AttachDTO photo;
    private LocalDate createdDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}
