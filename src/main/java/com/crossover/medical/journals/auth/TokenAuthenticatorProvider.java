package com.crossover.medical.journals.auth;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.crossover.medical.journals.core.UserRole;
import com.crossover.medical.journals.exception.ForbiddenException;
import com.google.common.base.Optional;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.api.model.Parameter;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

public class TokenAuthenticatorProvider<T> implements InjectableProvider<RestrictedTo, Parameter> {

    public final static String AUTHORIZATION_HEADER = "Authorization";

    private final Authenticator<TokenCredentials, T> authenticator;

    public TokenAuthenticatorProvider(Authenticator<TokenCredentials, T> authenticator) {
        this.authenticator = authenticator;
    }

    private static class TokenAuthenticatorInjectable<T> extends AbstractHttpContextInjectable<T> {
        private final Authenticator<TokenCredentials, T> authenticator;
        private final UserRole[] requiredRoles;

        private TokenAuthenticatorInjectable(Authenticator<TokenCredentials, T> authenticator,
                UserRole[] requiredRoles) {
            this.authenticator = authenticator;
            this.requiredRoles = requiredRoles;
        }

        @Override
        public T getValue(HttpContext c) {
            final String header = c.getRequest().getHeaderValue(AUTHORIZATION_HEADER);

            try {
                if (null == header) {
                    throw new AuthenticationException("Authorization header is missing");
                }

                final Optional<T> result = authenticator.authenticate(new TokenCredentials(header, requiredRoles));

                if (result.isPresent()) {
                    return result.get();
                }
            } catch (final AuthenticationException e) {
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
            } catch (final ForbiddenException e) {
                throw new WebApplicationException(Response.Status.FORBIDDEN);
            }

            return null;
        }
    }

    @Override
    public Injectable getInjectable(ComponentContext ic, RestrictedTo auth, Parameter parameter) {
        return new TokenAuthenticatorInjectable<T>(authenticator, auth.value());
    }

    @Override
    public ComponentScope getScope() {
        return ComponentScope.PerRequest;
    }
}
