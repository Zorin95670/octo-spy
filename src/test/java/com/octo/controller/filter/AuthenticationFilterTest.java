package com.octo.controller.filter;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.MultivaluedHashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.postgresql.util.Base64;

import com.octo.model.authentication.UserRoleType;
import com.octo.model.error.GlobalException;
import com.octo.service.UserService;
import com.octo.utils.Constants;

@ExtendWith(MockitoExtension.class)
class AuthenticationFilterTest {

    @Mock
    UserService userService;

    @Mock
    private ResourceInfo resourceInfo;

    @Mock
    private ContainerRequestContext context;

    @InjectMocks
    AuthenticationFilter filter = new AuthenticationFilter();

    private void mockUserMapping(String login) {
        String encodedUser = Base64.encodeBytes(new String(login + ":password").getBytes());

        MultivaluedHashMap<String, String> headers = new MultivaluedHashMap<>();
        headers.add(Constants.AUTHORIZATION_PROPERTY,
                String.format("%s %s", Constants.AUTHENTICATION_SCHEME, encodedUser));

        Mockito.when(context.getHeaders()).thenReturn(headers);
    }

    @Test
    void testFilter() throws NoSuchMethodException, SecurityException, IOException {
        Method method = MockClass.class.getDeclaredMethod("enumOne");
        Mockito.when(resourceInfo.getResourceMethod()).thenReturn(method);

        GlobalException exception = null;
        try {
            filter.filter(context);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);

        this.mockUserMapping("bad");
        method = MockClass.class.getDeclaredMethod("enumTwo");
        Mockito.when(resourceInfo.getResourceMethod()).thenReturn(method);
        exception = null;
        try {
            filter.filter(context);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);

        this.mockUserMapping(Constants.DEFAULT_ADMIN_LOGIN);
        Mockito.when(userService.isDefaultAdminitratorAllowed(Mockito.anyString())).thenReturn(false);
        exception = null;
        try {
            filter.filter(context);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);

        Mockito.when(userService.isDefaultAdminitratorAllowed(Mockito.anyString())).thenReturn(true);
        exception = null;
        try {
            filter.filter(context);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
    }

    static class MockClass {
        @PermitAll
        public void enumOne() {
        }

        @RolesAllowed(UserRoleType.ALL)
        public void enumTwo() {
        }
    }
}
