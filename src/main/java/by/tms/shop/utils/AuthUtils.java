package by.tms.shop.utils;

import by.tms.shop.configs.security.CustomUserDetails;
import by.tms.shop.entities.User;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class AuthUtils {

    public User getCurrentUser() {
        return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUser();
    }
}
