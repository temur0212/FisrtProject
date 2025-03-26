package api.giybat.uz.dto.Sms;

import lombok.Data;


public class SmsAuthResposeDTO {
    private SmsAuthResponseDataDTO data;

    public SmsAuthResponseDataDTO getData() {
        return data;
    }

    public void setData(SmsAuthResponseDataDTO data) {
        this.data = data;
    }
}
