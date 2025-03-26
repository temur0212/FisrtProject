package api.giybat.uz.config;

import api.giybat.uz.entity.ProfileEntity;
import api.giybat.uz.enums.ProfileRoles;
import api.giybat.uz.repozitory.ProfileRepository;
import api.giybat.uz.repozitory.ProfileRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileRoleRepository profileRoleRepository;
    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(username);
        if (optional.isEmpty())
        {
             throw new UsernameNotFoundException("Username not found");
        }
        ProfileEntity profileEntity = optional.get();
        List<ProfileRoles> roles = profileRoleRepository.getAllRolesListByProfileId(profileEntity.getId());

        return new CustomUserDetails(profileEntity,roles);

    }

}
