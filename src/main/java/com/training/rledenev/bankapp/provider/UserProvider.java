package com.training.rledenev.bankapp.provider;

import com.training.rledenev.bankapp.entity.User;
import com.training.rledenev.bankapp.entity.security.CustomUserDetails;
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
