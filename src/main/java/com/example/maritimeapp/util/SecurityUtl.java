package com.example.maritimeapp.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class SecurityUtl {

    private SecurityUtl() {
        throw new IllegalStateException("Utility class cannot be instantiated.");
    }

    public static User getLoggedInUser() {
        return (User) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();
    }
}
