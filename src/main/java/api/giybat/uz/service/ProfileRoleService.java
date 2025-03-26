package api.giybat.uz.service;

import api.giybat.uz.entity.ProfileRolesEntity;
import api.giybat.uz.enums.ProfileRoles;
import api.giybat.uz.repozitory.ProfileRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProfileRoleService {

    @Autowired
    private ProfileRoleRepository profileRoleRepository;

    public void createProfileRole(Long profileId, ProfileRoles role) {
        ProfileRolesEntity profileRolesEntity = new ProfileRolesEntity();
        profileRolesEntity.setProfileId(profileId);
        profileRolesEntity.setRoles(role);
        profileRolesEntity.setCreatedDate(LocalDate.now());
        profileRolesEntity.setUpdatedDate(LocalDate.now());
        profileRoleRepository.save(profileRolesEntity);

    }

    public void deleteProfileRole(Long profileId) {
        profileRoleRepository.deleteByProfileId(profileId);
    }

    public List<ProfileRoles> getProfileRoles(Long id) {
        return profileRoleRepository.getAllRolesListByProfileId(id);
    }
}
