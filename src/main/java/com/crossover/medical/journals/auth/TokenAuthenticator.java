package com.crossover.medical.journals.auth;

import com.crossover.medical.journals.core.User;
import com.google.common.base.Optional;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

public class TokenAuthenticator implements Authenticator<TokenCredentials, User> {

    private final AuthenticationManager authenticatorManager;

    public TokenAuthenticator(AuthenticationManager authenticatorManager) {
        this.authenticatorManager = authenticatorManager;
    }

    @Override
    public Optional<User> authenticate(TokenCredentials credentials) throws AuthenticationException {
        final Optional<User> user = authenticatorManager.findOneByToken(credentials.getToken());

        if (isInvalidToken(credentials, user)) {
            throw new AuthenticationException("Invalid credentials");
        }

        return user;
    }

    private boolean isInvalidToken(TokenCredentials credentials, final Optional<User> user) {
        return !user.isPresent() || !user.get().getToken().equals(credentials.getToken());
    }
}
