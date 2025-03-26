package api.giybat.uz.repozitory;

import api.giybat.uz.entity.PostEntity;
import api.giybat.uz.enums.GeneralStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, String> {

    List<PostEntity> findAllByUserIdAndVisibleTrue(Long userId);

    @Modifying
    @Transactional
    @Query("update PostEntity set visible = false where id = ?1")
    void vizibility(String id);

    @Query("select p.user.id from PostEntity p where p.id = ?1")
    Long findUserIdByPostId(String postId);



}
