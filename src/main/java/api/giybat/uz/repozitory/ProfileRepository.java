package api.giybat.uz.repozitory;

import api.giybat.uz.entity.ProfileEntity;
import api.giybat.uz.enums.GeneralStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
   Optional<ProfileEntity> findByUsernameAndVisibleTrue(String username);
   Optional<ProfileEntity> findByIdAndVisibleTrue(Long id);


   // update status
   @Modifying
   @Transactional
   @Query("update ProfileEntity p set p.status = ?2 where p.id = ?1")
   void changeStatusById(Long id, GeneralStatus status);

   // update name
   @Modifying
   @Transactional
   @Query("update ProfileEntity p set p.name = ?2 where p.id = ?1")
   void updateNameById(Long id, String name);

   //update password
   @Modifying
   @Transactional
   @Query("update ProfileEntity p set p.password = ?2 where p.id = ?1")
   void updatePasswordById(Long id, String password);

   // update temp_username
   @Modifying
   @Transactional
   @Query("update ProfileEntity p set p.temp_username = ?2 where p.id = ?1")
   void updateTempUsernameById(Long id, String temp_name);

   // update username
   @Modifying
   @Transactional
   @Query("update ProfileEntity p set p.username = ?2 where p.id = ?1")
   void updateUsernameById(Long id, String username);

   @Modifying
   @Transactional
   @Query("update ProfileEntity p set p.photoId = ?2 where p.id = ?1")
   void updatePhotoById(Long id, String photoId);




}
