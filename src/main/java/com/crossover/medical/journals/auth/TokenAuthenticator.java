package com.crossover.medical.journals.auth;

import java.util.Set;

import com.crossover.medical.journals.core.User;
import com.crossover.medical.journals.core.UserRole;
import com.crossover.medical.journals.exception.ForbiddenException;
import com.google.common.base.Optional;
import com.google.common.collect.Sets;

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

        if (!isGranted(user.get().getRole(), credentials.getRequiredRoles())) {
            throw new ForbiddenException("Forbidden");
        }

        return user;
    }

    private boolean isInvalidToken(TokenCredentials credentials, final Optional<User> user) {
        return !user.isPresent() || !user.get().getToken().equals(credentials.getToken());
    }

    public boolean isGranted(UserRole userRole, UserRole[] requiredRoles) {
        final Set<UserRole> grantedRoles = Sets.newHashSet();

        for (final UserRole requiredRole : requiredRoles) {
            grantedRoles.add(requiredRole);
        }

        return grantedRoles.contains(userRole);
    }
}
