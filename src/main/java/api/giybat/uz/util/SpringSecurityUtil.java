package api.giybat.uz.util;

import api.giybat.uz.config.CustomUserDetails;
import api.giybat.uz.enums.ProfileRoles;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class SpringSecurityUtil {
    public static CustomUserDetails getCurrentEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            return (CustomUserDetails) authentication.getPrincipal();
        }
        throw new IllegalStateException("Foydalanuvchi tizimga kirmagan yoki noto‘g‘ri principal turi");
    }

    public static Long getCurrentUserId() {
        return getCurrentEntity().getId();
    }

    public static Boolean hasRole(ProfileRoles profileRoles)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority().equals(profileRoles.name()));
    }
}


