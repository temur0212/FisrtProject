package api.giybat.uz.dto.Sms;

import lombok.Data;


public class SmsAuthResponseDataDTO {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
