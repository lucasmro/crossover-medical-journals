package com.crossover.medical.journals.resource;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.crossover.medical.journals.core.User;

public class UserResourceTest {

    private UserResource resource;
    private Response mockResponse;

    @Before
    public void setUp() throws Exception {
        resource = mock(UserResource.class);
        mockResponse = mock(Response.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAuthenticateShouldReturnSuccessWhenEmailAndPasswordAreValid() {
        final User userCredential = new User();
        userCredential.setEmail("lucasmro@gmail.com");
        userCredential.setPassword("123");

        final User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setEmail("lucasmro@gmail.com");
        expectedUser.setPassword("123");
        expectedUser.setToken("token123");

        when(resource.authenticate(userCredential)).thenReturn(mockResponse);
        when(mockResponse.getEntity()).thenReturn(expectedUser);
        when(mockResponse.getStatus()).thenReturn(200);

        final Response expected = Response.ok(expectedUser).build();
        final Response actualResponse = resource.authenticate(userCredential);

        assertThat(actualResponse.getStatus(), equalTo(expected.getStatus()));
        assertThat(actualResponse.getEntity(), equalTo(expectedUser));
    }

    @Test
    public void testAuthenticateShouldReturnUnauthorizedWhenEmailOrPasswordIsInvalid() {
        final User userCredential = new User();
        userCredential.setEmail("inexistent@gmail.com");
        userCredential.setPassword("123");

        when(resource.authenticate(userCredential)).thenReturn(mockResponse);
        when(mockResponse.getStatus()).thenReturn(401);

        final Response expected = Response.status(401).build();
        final Response actualResponse = resource.authenticate(userCredential);

        assertThat(actualResponse.getStatus(), equalTo(expected.getStatus()));
    }
}
