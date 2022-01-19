package com.octo.security;

import com.octo.model.common.Constants;
import com.octo.model.error.GlobalException;
import com.octo.persistence.model.User;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedHashMap;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class UserMapperTest {

    @Test
    void testNoAuthorization() {
        MultivaluedHashMap<String, String> headers = new MultivaluedHashMap<>();
        ContainerRequestContext context = Mockito.mock(ContainerRequestContext.class);

        Mockito.when(context.getHeaders()).thenReturn(headers);

        GlobalException exception = null;
        try {
            new UserMapper().apply(context);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);

        headers.add(Constants.AUTHORIZATION_PROPERTY, null);
        Mockito.when(context.getHeaders()).thenReturn(headers);

        exception = null;
        try {
            new UserMapper().apply(context);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @Test
    void testBadScheme() {
        ContainerRequestContext context = Mockito.mock(ContainerRequestContext.class);

        MultivaluedHashMap<String, String> headers = new MultivaluedHashMap<>();
        headers.add(Constants.AUTHORIZATION_PROPERTY, "");

        Mockito.when(context.getHeaders()).thenReturn(headers);

        GlobalException exception = null;
        try {
            new UserMapper().apply(context);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @Test
    void testBasicAuthentication() {
        ContainerRequestContext context = Mockito.mock(ContainerRequestContext.class);

        String encodedUser = Base64.getUrlEncoder().encodeToString("login:password".getBytes());

        MultivaluedHashMap<String, String> headers = new MultivaluedHashMap<>();
        headers.add(Constants.AUTHORIZATION_PROPERTY,
                String.format("%s %s", Constants.AUTHENTICATION_BASIC_SCHEME, encodedUser));

        Mockito.when(context.getHeaders()).thenReturn(headers);

        GlobalException exception = null;
        User user = null;
        try {
            user = new UserMapper().apply(context);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
        assertNotNull(user);
        assertEquals("login", user.getLogin());
        assertEquals("password", user.getPassword());
        assertEquals(Constants.AUTHENTICATION_BASIC_SCHEME, user.getAuthenticationType());
    }

    @Test
    void testTokenAuthentication() {
        ContainerRequestContext context = Mockito.mock(ContainerRequestContext.class);

        String encodedUser = Base64.getUrlEncoder().encodeToString("token".getBytes());

        MultivaluedHashMap<String, String> headers = new MultivaluedHashMap<>();
        headers.add(Constants.AUTHORIZATION_PROPERTY,
                String.format("%s %s", Constants.AUTHENTICATION_TOKEN_SCHEME, encodedUser));

        Mockito.when(context.getHeaders()).thenReturn(headers);

        GlobalException exception = null;
        User user = null;
        try {
            user = new UserMapper().apply(context);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
        assertNotNull(user);
        assertEquals("token", user.getPassword());
        assertEquals(Constants.AUTHENTICATION_TOKEN_SCHEME, user.getAuthenticationType());
    }
}
