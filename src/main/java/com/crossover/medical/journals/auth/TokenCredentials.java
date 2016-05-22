package com.crossover.medical.journals.auth;

public class TokenCredentials {
    private final String token;

    public TokenCredentials(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
