package com.octo.security;

import com.octo.model.common.Constants;
import com.octo.model.common.UserRoleType;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.persistence.model.User;
import com.octo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.MultivaluedHashMap;
import java.lang.reflect.Method;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationFilterTest {

    @Mock
    UserService userService;
    @InjectMocks
    AuthenticationFilter filter = new AuthenticationFilter();
    @Mock
    private ResourceInfo resourceInfo;
    @Mock
    private ContainerRequestContext context;

    private void mockUserMapping(String data, String authenticationType) {
        String encodedUser = Base64.getUrlEncoder().encodeToString(data.getBytes());

        MultivaluedHashMap<String, String> headers = new MultivaluedHashMap<>();
        headers.add(Constants.AUTHORIZATION_PROPERTY, String.format("%s %s", authenticationType, encodedUser));

        Mockito.when(context.getHeaders()).thenReturn(headers);
    }

    @Test
    void testBadRoles() throws NoSuchMethodException, SecurityException {
        Method method = MockClass.class.getDeclaredMethod("enumThree");
        Mockito.when(resourceInfo.getResourceMethod()).thenReturn(method);

        GlobalException exception = null;
        try {
            filter.filter(context);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getError().getMessage(), ErrorType.INTERNAL_ERROR.getMessage());
    }

    @Test
    void testPermitAll() throws NoSuchMethodException, SecurityException {
        Method method = MockClass.class.getDeclaredMethod("enumOne");
        Mockito.when(resourceInfo.getResourceMethod()).thenReturn(method);

        GlobalException exception = null;
        try {
            filter.filter(context);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
    }

    @Test
    void testBasicAuthentication() throws NoSuchMethodException, SecurityException {
        this.mockUserMapping("bad:password", Constants.AUTHENTICATION_BASIC_SCHEME);
        Method method = MockClass.class.getDeclaredMethod("enumTwo");
        Mockito.when(resourceInfo.getResourceMethod()).thenReturn(method);
        GlobalException exception = null;
        try {
            filter.filter(context);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);

        this.mockUserMapping(Constants.DEFAULT_ADMIN_LOGIN + ":password", Constants.AUTHENTICATION_BASIC_SCHEME);
        Mockito.when(userService.isDefaultAdministratorAllowed(Mockito.anyString())).thenReturn(false);
        exception = null;
        try {
            filter.filter(context);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);

        Mockito.when(userService.isDefaultAdministratorAllowed(Mockito.anyString())).thenReturn(true);
        exception = null;
        try {
            filter.filter(context);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
    }

    @Test
    void testTokenAuthenticationWithoutGoodRole() throws NoSuchMethodException, SecurityException {
        this.mockUserMapping("token", Constants.AUTHENTICATION_TOKEN_SCHEME);
        Method method = MockClass.class.getDeclaredMethod("enumTwo");
        Mockito.when(resourceInfo.getResourceMethod()).thenReturn(method);

        GlobalException exception = null;
        try {
            filter.filter(context);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getError().getMessage(), ErrorType.AUTHORIZATION_ERROR.getMessage());
    }

    @Test
    void testTokenAuthenticationNoUser() throws NoSuchMethodException, SecurityException {
        this.mockUserMapping("token", Constants.AUTHENTICATION_TOKEN_SCHEME);
        Method method = MockClass.class.getDeclaredMethod("enumFour");
        Mockito.when(resourceInfo.getResourceMethod()).thenReturn(method);
        Mockito.when(userService.getUserFromToken(Mockito.anyString())).thenReturn(null);

        GlobalException exception = null;
        try {
            filter.filter(context);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getError().getMessage(), ErrorType.AUTHORIZATION_FAILED.getMessage());
    }

    @Test
    void testTokenAuthenticationBadLogin() throws NoSuchMethodException, SecurityException {
        User user = new User();
        user.setLogin("bad");
        this.mockUserMapping("token", Constants.AUTHENTICATION_TOKEN_SCHEME);
        Method method = MockClass.class.getDeclaredMethod("enumFour");
        Mockito.when(resourceInfo.getResourceMethod()).thenReturn(method);
        Mockito.when(userService.getUserFromToken(Mockito.anyString())).thenReturn(user);

        GlobalException exception = null;
        try {
            filter.filter(context);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getError().getMessage(), ErrorType.AUTHORIZATION_FAILED.getMessage());
    }

    @Test
    void testTokenAuthentication() throws NoSuchMethodException, SecurityException {
        User user = new User();
        user.setLogin(Constants.DEFAULT_ADMIN_LOGIN);
        this.mockUserMapping("token", Constants.AUTHENTICATION_TOKEN_SCHEME);
        Method method = MockClass.class.getDeclaredMethod("enumFour");
        Mockito.when(resourceInfo.getResourceMethod()).thenReturn(method);
        Mockito.when(userService.getUserFromToken(Mockito.anyString())).thenReturn(user);

        GlobalException exception = null;
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

        public void enumThree() {
        }

        @RolesAllowed({UserRoleType.ALL, UserRoleType.TOKEN})
        public void enumFour() {
        }
    }
}
