package api.giybat.uz.repozitory;

import api.giybat.uz.entity.SmsHistoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SmsHistoryRepository extends JpaRepository<SmsHistoryEntity, String> {

    Long countByPhoneAndCreatedDateBetween(String phone, LocalDateTime createdDateAfter, LocalDateTime createdDateBefore);
    // Select from sms_history where phone = ? created_date order by created_date desc limit 1
    Optional<SmsHistoryEntity> findFirstByPhoneOrderByCreatedDateDesc(String phone);

    @Modifying
    @Transactional
    @Query("update SmsHistoryEntity set smsLimit = coalesce(smsLimit,0) + 1 where id = ?1")
    void updateSmsLimitById(String id);

}
