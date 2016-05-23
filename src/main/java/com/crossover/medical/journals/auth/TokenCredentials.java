package com.crossover.medical.journals.auth;

import com.crossover.medical.journals.core.UserRole;

public class TokenCredentials {
    private final String token;
    private final UserRole[] requiredRoles;

    public TokenCredentials(String token, UserRole[] requiredRoles) {
        this.token = token;
        this.requiredRoles = requiredRoles;
    }

    public String getToken() {
        return token;
    }

    public UserRole[] getRequiredRoles() {
        return requiredRoles;
    }
}
