package com.octo.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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
    void testUpdateDefaultAdminitratorPassword() {
        Mockito.when(passwordEncoder.encode(Mockito.any())).thenReturn(null);
        Mockito.when(userDAO.save(Mockito.any())).thenReturn(null);
        Mockito.when(userDAO.loadWithLock(Mockito.any())).thenReturn(Optional.empty());

        GlobalException exception = null;
        try {
            service.updateDefaultAdminitratorPassword("password");
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);

        Mockito.when(userDAO.loadWithLock(Mockito.any())).thenReturn(Optional.of(new User()));

        exception = null;
        try {
            service.updateDefaultAdminitratorPassword("password");
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
    void testIsSecureAdministrator() {
        Mockito.when(userDAO.load(Mockito.any())).thenReturn(Optional.empty());
        assertTrue(service.isSecureAdministrator());

        User user = new User();
        user.setActive(false);
        Mockito.when(userDAO.load(Mockito.any())).thenReturn(Optional.of(user));
        assertTrue(service.isSecureAdministrator());

        Mockito.when(passwordEncoder.matches(Mockito.any(), Mockito.any())).thenReturn(true);
        assertTrue(service.isSecureAdministrator());

        user.setActive(true);
        Mockito.when(userDAO.load(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches(Mockito.any(), Mockito.any())).thenReturn(false);
        assertTrue(service.isSecureAdministrator());

        Mockito.when(passwordEncoder.matches(Mockito.any(), Mockito.any())).thenReturn(true);
        assertFalse(service.isSecureAdministrator());
    }

    @Test
    void testIsValidAdministratorEmail() {
        Mockito.when(userDAO.load(Mockito.any())).thenReturn(Optional.empty());
        assertTrue(service.isValidAdministratorEmail());

        User user = new User();
        user.setActive(false);
        Mockito.when(userDAO.load(Mockito.any())).thenReturn(Optional.of(user));
        assertTrue(service.isValidAdministratorEmail());

        user.setEmail("test");
        Mockito.when(userDAO.load(Mockito.any())).thenReturn(Optional.of(user));
        assertTrue(service.isValidAdministratorEmail());

        user.setActive(true);
        Mockito.when(userDAO.load(Mockito.any())).thenReturn(Optional.of(user));
        assertTrue(service.isValidAdministratorEmail());

        user.setEmail("no-reply@change.it");
        Mockito.when(userDAO.load(Mockito.any())).thenReturn(Optional.of(user));
        assertFalse(service.isValidAdministratorEmail());

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
