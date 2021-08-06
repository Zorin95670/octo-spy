package com.octo.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.CharBuffer;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.postgresql.util.Base64;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.octo.dao.IDAO;
import com.octo.model.authentication.UserRoleType;
import com.octo.model.entity.User;
import com.octo.model.error.GlobalException;
import com.octo.utils.Constants;
import com.octo.utils.predicate.filter.QueryFilter;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    IDAO<User, QueryFilter> userDAO;

    @Mock
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @InjectMocks
    UserService service;

    @Test
    void testUpdateDefaultAdminitratorPasswordWithBadLength() {
        String tooShortPassword = CharBuffer.allocate(7).toString().replace('\0', 'a');
        String tooLongPassword = CharBuffer.allocate(51).toString().replace('\0', 'a');

        GlobalException exception = null;
        try {
            service.updateDefaultAdminitratorPassword("");
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);

        exception = null;
        try {
            String encodedPassword = Base64.encodeBytes(tooShortPassword.getBytes());
            service.updateDefaultAdminitratorPassword(encodedPassword);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        exception = null;
        try {
            String encodedPassword = Base64.encodeBytes(tooLongPassword.getBytes());
            service.updateDefaultAdminitratorPassword(encodedPassword);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @Test
    void testUpdateDefaultAdminitratorPassword() {
        Mockito.when(passwordEncoder.encode(Mockito.any())).thenReturn(null);
        Mockito.when(userDAO.save(Mockito.any())).thenReturn(null);
        Mockito.when(userDAO.loadWithLock(Mockito.any())).thenReturn(Optional.empty());

        GlobalException exception = null;
        try {
            service.updateDefaultAdminitratorPassword("password1234");
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);

        Mockito.when(userDAO.loadWithLock(Mockito.any())).thenReturn(Optional.of(new User()));

        exception = null;
        try {
            service.updateDefaultAdminitratorPassword("password1234");
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
    }

    @Test
    void testUpdateDefaultAdminitratorEmail() {
        GlobalException exception = null;
        try {
            service.updateDefaultAdminitratorEmail("bad email");
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        Mockito.when(userDAO.save(Mockito.any())).thenReturn(null);
        Mockito.when(userDAO.loadWithLock(Mockito.any())).thenReturn(Optional.empty());
        String validEmail = "testemail@test.com";
        exception = null;
        try {
            service.updateDefaultAdminitratorEmail(validEmail);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);

        Mockito.when(userDAO.loadWithLock(Mockito.any())).thenReturn(Optional.of(new User()));
        exception = null;
        try {
            service.updateDefaultAdminitratorEmail(validEmail);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
    }

    @Test
    void testIsDefaultAdminitratorAllowed() {
        Mockito.when(userDAO.load(Mockito.any())).thenReturn(Optional.empty());
        assertFalse(service.isDefaultAdminitratorAllowed("password"));

        User user = new User();
        user.setActive(false);
        Mockito.when(userDAO.load(Mockito.any())).thenReturn(Optional.of(user));
        assertFalse(service.isDefaultAdminitratorAllowed("password"));

        user.setActive(true);
        Mockito.when(userDAO.load(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches(Mockito.any(), Mockito.any())).thenReturn(false);
        assertFalse(service.isDefaultAdminitratorAllowed("password"));

        Mockito.when(passwordEncoder.matches(Mockito.any(), Mockito.any())).thenReturn(true);
        assertTrue(service.isDefaultAdminitratorAllowed("password"));
    }

    @Test
    void testGetUser() {
        Mockito.when(userDAO.load(Mockito.any())).thenReturn(Optional.empty());
        assertNull(service.getUser("test"));

        Mockito.when(userDAO.load(Mockito.any())).thenReturn(Optional.of(new User()));
        assertNotNull(service.getUser("test"));
    }

    @Test
    void testGetUserRoles() {
        List<String> roles = service.getUserRoles("test");
        assertNotNull(roles);
        assertTrue(roles.isEmpty());

        roles = service.getUserRoles(Constants.DEFAULT_ADMIN_LOGIN);
        assertNotNull(roles);
        assertEquals(1, roles.size());
        assertEquals(UserRoleType.ADMIN, roles.get(0));
    }
}
