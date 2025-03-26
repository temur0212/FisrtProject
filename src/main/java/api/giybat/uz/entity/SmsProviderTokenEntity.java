package api.giybat.uz.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "sms_provider_token")
public class SmsProviderTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 2048 , name = "token")
    private String token;


    @Column(name = "created_date")
    private LocalDate createdDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}


