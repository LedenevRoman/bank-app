package com.training.rledenev.provider;

import com.training.rledenev.entity.User;
import com.training.rledenev.entity.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserProvider {

    public User getCurrentUser() {
        return ((CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser();
    }
}
