package api.giybat.uz.service;

import api.giybat.uz.entity.SmsHistoryEntity;
import api.giybat.uz.enums.AppLanguage;
import api.giybat.uz.enums.SmsType;
import api.giybat.uz.exps.AppBadExseption;
import api.giybat.uz.repozitory.SmsHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SmsHistoryService {

    @Autowired
    private SmsHistoryRepository smsHistoryRepository;

    @Autowired
    private LanguageMessageService languageMessage;


    public void saveSmsHistory(String phone, String message,String code, SmsType smsType) {
        SmsHistoryEntity smsHistoryEntity = new SmsHistoryEntity();
        smsHistoryEntity.setPhone(phone);
        smsHistoryEntity.setMessage(message);
        smsHistoryEntity.setCode(code);
        smsHistoryEntity.setSmsType(smsType);
        smsHistoryEntity.setSmsLimit(0);
        smsHistoryEntity.setCreatedDate(LocalDateTime.now());
        smsHistoryRepository.save(smsHistoryEntity);
    }

    public Long getSmsCount(String phoneNumber) {
        return smsHistoryRepository.countByPhoneAndCreatedDateBetween(phoneNumber,LocalDateTime.now().minusMinutes(2),LocalDateTime.now());
    }

    public Boolean check(String Phone , String Code , AppLanguage lang){
        // find last sms by phone
        Optional<SmsHistoryEntity> optional =  smsHistoryRepository.findFirstByPhoneOrderByCreatedDateDesc(Phone);
        if (optional.isEmpty()){
            throw new AppBadExseption(languageMessage.getMessage("sms.not.exist", lang));
        }
        SmsHistoryEntity smsHistoryEntity = optional.get();

        if (smsHistoryEntity.getSmsLimit()>=3){
            throw new AppBadExseption(languageMessage.getMessage("sms.limit.error", lang));
        }
        // check code
        if (!smsHistoryEntity.getCode().equals(Code)){
            smsHistoryEntity.setSmsLimit(smsHistoryEntity.getSmsLimit()+1);  // increase sms limit
            smsHistoryRepository.updateSmsLimitById(smsHistoryEntity.getId());
            throw new AppBadExseption(languageMessage.getMessage("sms.code.error", lang));
        }
        // check time
        LocalDateTime expiredDate = smsHistoryEntity.getCreatedDate().plusMinutes(2);
        if (LocalDateTime.now().isAfter(expiredDate)){
            throw new AppBadExseption(languageMessage.getMessage("sms.time.error", lang));
        }
        return true;
    }
}
