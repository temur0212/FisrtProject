package api.giybat.uz.service;

import api.giybat.uz.dto.Sms.SmsAuthDTO;
import api.giybat.uz.dto.Sms.SmsAuthResposeDTO;
import api.giybat.uz.dto.Sms.SmsRequestDTO;
import api.giybat.uz.dto.Sms.SmsSendResponseDTO;
import api.giybat.uz.entity.SmsProviderTokenEntity;
import api.giybat.uz.enums.SmsType;
import api.giybat.uz.exps.AppBadExseption;
import api.giybat.uz.repozitory.SmsProviderTokenRepository;
import api.giybat.uz.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class SmsSendService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SmsProviderTokenRepository smsProviderTokenRepository;

    @Autowired
    private SmsHistoryService smsHistoryService;



    @Value(value = "${sms.api.url}")
    private String SmsApiUrl;

    @Value(value = "${sms.email}")
    private String SmsApiEmail;

    @Value(value = "${sms.password}")
    private String SmsApiPassword;

    private final Long smsLimit = 3L;

    public  void sendRegistrationSms(String phoneNumber) {
        String Constants = "Bu Eskiz dan test";
//        String code = "12345";
        String code = RandomUtil.getRandomValue();

        CompletableFuture.runAsync(() ->

        sendSms(phoneNumber,Constants,code,SmsType.REGISTRATION));
    }

    public void sendResetPasswordSms(String phone) {
        String Constants = "Bu Eskiz dan test";
//        String code = "12345";
        String code = RandomUtil.getRandomValue();

        CompletableFuture.runAsync(() ->

                sendSms(phone,Constants,code,SmsType.FORGOT_PASSWORD));
    }

    public void sendUpdateUsernameSms(String phone) {
        String Constants = "Bu Eskiz dan test";
//        String code = "12345";
        String code = RandomUtil.getRandomValue();

        CompletableFuture.runAsync(() ->

                sendSms(phone,Constants,code,SmsType.CHANGE_PHONE));
    }

    private void sendSms(String phone, String message, String code , SmsType smsType) {
        //check
        Long count = smsHistoryService.getSmsCount(phone);
        if (smsLimit<=count)
        {
            System.out.println("Sms limit reached. Phone : "+phone);
            throw new AppBadExseption("Sms limit reached");
        }
        // sms send phone
        //sendSms(phone, message);
        smsHistoryService.saveSmsHistory(phone,message,code,smsType);
    }

    private void sendSms(String phone, String message) {
        String token = getToken();
        // request header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer "+token);
        // request body
        SmsRequestDTO smsRequestDTO = new SmsRequestDTO();
        smsRequestDTO.setMobile_phone(phone);
        smsRequestDTO.setMessage(message);
        smsRequestDTO.setFrom("4546");
        // request
        try {
            HttpEntity<SmsRequestDTO> request = new HttpEntity<>(smsRequestDTO, headers);
            String url = SmsApiUrl+"/message/sms/send";
            ResponseEntity<SmsSendResponseDTO> response =restTemplate.
                            exchange(url,HttpMethod.POST,
                            request,
                            SmsSendResponseDTO.class);
            response.getBody();
        }catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String getToken() {
        Optional<SmsProviderTokenEntity> optional = smsProviderTokenRepository.findById(1);
        SmsProviderTokenEntity smsProviderTokenEntity;

        // Agar token mavjud bo‘lmasa, yangi yaratamiz
        if (optional.isEmpty()) {
            smsProviderTokenEntity = new SmsProviderTokenEntity();
            smsProviderTokenEntity.setId(1);
            smsProviderTokenEntity.setToken(getTokenFromProvider());
            smsProviderTokenEntity.setCreatedDate(LocalDate.now());
            smsProviderTokenRepository.save(smsProviderTokenEntity);
            return smsProviderTokenEntity.getToken(); // Yangi tokenni qaytarish
        } else {
            smsProviderTokenEntity = optional.get(); // Agar mavjud bo‘lsa, olib ishlaymiz
        }

        // Token muddati 30 kundan eski bo'lmasa, eski tokenni qaytaramiz
        if (smsProviderTokenEntity.getCreatedDate().isAfter(LocalDate.now().minusDays(30))) {
           // System.out.println(smsProviderTokenEntity.getToken());
            return smsProviderTokenEntity.getToken();


        }

        // Yangi token olish
        String newToken = getTokenFromProvider();

        // Tokenni bazaga saqlash
        smsProviderTokenEntity.setToken(newToken);
        smsProviderTokenEntity.setCreatedDate(LocalDate.now());
        smsProviderTokenRepository.save(smsProviderTokenEntity);
        System.out.println(newToken);
        return newToken;
    }

    private String getTokenFromProvider() {
        SmsAuthDTO smsAuthDTO = new SmsAuthDTO();
        smsAuthDTO.setEmail(SmsApiEmail);
        smsAuthDTO.setPassword(SmsApiPassword);


        try {
            SmsAuthResposeDTO response = restTemplate.postForObject(SmsApiUrl+"/auth/login",smsAuthDTO, SmsAuthResposeDTO.class);
            return response.getData().getToken();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }





}
