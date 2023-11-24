package com.training.rledenev.security;

import com.training.rledenev.entity.User;
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
