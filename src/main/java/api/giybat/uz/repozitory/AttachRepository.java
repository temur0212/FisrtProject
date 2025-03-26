package api.giybat.uz.repozitory;

import api.giybat.uz.entity.AttachEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AttachRepository extends JpaRepository<AttachEntity, String> {


    @Modifying
    @Transactional
    @Query("update AttachEntity set visible = false where id = ?1")
    void vizibility(String id);



}