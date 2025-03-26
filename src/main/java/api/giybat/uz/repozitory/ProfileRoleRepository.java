package api.giybat.uz.repozitory;

import api.giybat.uz.entity.ProfileRolesEntity;
import api.giybat.uz.enums.ProfileRoles;
import jakarta.persistence.ManyToOne;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfileRoleRepository extends CrudRepository<ProfileRolesEntity,Long> {
    @Transactional
    @Modifying
    void deleteByProfileId(Long profileId);

//    List<ProfileRoles> findRolesByProfileId(Long id);

    @Query("select p.roles from ProfileRolesEntity p where p.profileId = ?1")
    List<ProfileRoles> getAllRolesListByProfileId(Long id);
}
