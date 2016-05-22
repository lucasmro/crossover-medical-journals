package com.crossover.medical.journals.auth;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.google.common.base.Optional;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.api.model.Parameter;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;

import io.dropwizard.auth.Auth;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

public class TokenAuthenticatorProvider<T> implements InjectableProvider<Auth, Parameter> {

    public final static String AUTHORIZATION_HEADER = "Authorization";

    private final Authenticator<TokenCredentials, T> authenticator;

    public TokenAuthenticatorProvider(Authenticator<TokenCredentials, T> authenticator) {
        this.authenticator = authenticator;
    }

    private static class TokenAuthenticatorInjectable<T> extends AbstractHttpContextInjectable<T> {
        private final Authenticator<TokenCredentials, T> authenticator;
        private final boolean required;

        private TokenAuthenticatorInjectable(Authenticator<TokenCredentials, T> authenticator, boolean required) {
            this.authenticator = authenticator;
            this.required = required;
        }

        @Override
        public T getValue(HttpContext c) {
            final String header = c.getRequest().getHeaderValue(AUTHORIZATION_HEADER);

            try {
                if (null == header) {
                    throw new AuthenticationException("Authorization header is missing");
                }

                final Optional<T> result = authenticator.authenticate(new TokenCredentials(header));

                if (result.isPresent()) {
                    return result.get();
                }
            } catch (final AuthenticationException e) {
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
            }

            if (required) {
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
            }

            return null;
        }
    }

    @Override
    public Injectable getInjectable(ComponentContext ic, Auth auth, Parameter parameter) {
        return new TokenAuthenticatorInjectable<T>(authenticator, auth.required());
    }

    @Override
    public ComponentScope getScope() {
        return ComponentScope.PerRequest;
    }
}
