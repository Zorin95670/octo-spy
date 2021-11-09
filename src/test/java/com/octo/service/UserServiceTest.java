package com.octo.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.CharBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
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
import com.octo.model.dto.user.token.SearchUserTokenDTO;
import com.octo.model.entity.User;
import com.octo.model.entity.UserToken;
import com.octo.model.error.GlobalException;
import com.octo.utils.Constants;
import com.octo.utils.predicate.filter.QueryFilter;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    IDAO<User, QueryFilter> userDAO;

    @Mock
    IDAO<UserToken, QueryFilter> userTokenDAO;

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
            String encodedPassword = Base64.getUrlEncoder().encodeToString(tooShortPassword.getBytes());
            service.updateDefaultAdminitratorPassword(encodedPassword);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        exception = null;
        try {
            String encodedPassword = Base64.getUrlEncoder().encodeToString(tooLongPassword.getBytes());
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

    @Test
    void testGetUserFromToken() {
        UserToken token = new UserToken();
        token.setUser(new User());
        Mockito.when(this.userTokenDAO.load(Mockito.any())).thenReturn(Optional.of(token));
        assertNotNull(service.getUserFromToken("token"));

        Mockito.when(this.userTokenDAO.load(Mockito.any())).thenReturn(Optional.empty());
        assertNull(service.getUserFromToken("token"));
    }

    @Test
    void testGetUserTokenUnknownUser() {
        Mockito.when(this.userDAO.load(Mockito.any())).thenReturn(Optional.empty());
        GlobalException exception = null;
        try {
            this.service.getUserToken("test");
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @Test
    void testGetUserToken() {
        User user = new User();
        user.setId(1L);
        UserToken token1 = new UserToken();
        token1.setName("token1");
        UserToken token2 = new UserToken();
        token2.setName("token2");
        List<UserToken> tokens = List.of(token1, token2);
        Mockito.when(this.userDAO.load(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(this.userTokenDAO.find(Mockito.any())).thenReturn(tokens);

        assertEquals(List.of("token1", "token2"), service.getUserToken("admin"));
    }

    @Test
    void testCreateDuplicateToken() throws NoSuchAlgorithmException {
        User user = new User();
        user.setId(1L);

        UserToken userToken = new UserToken();
        userToken.setName("TOKEN");

        Mockito.when(this.userDAO.load(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(this.userTokenDAO.find(Mockito.any())).thenReturn(List.of(userToken));

        GlobalException exception = null;
        try {
            this.service.createToken("test", "token");
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @Test
    void testCreateTokenUnknownUser() throws NoSuchAlgorithmException {
        Mockito.when(this.userDAO.load(Mockito.any())).thenReturn(Optional.empty());

        GlobalException exception = null;
        try {
            this.service.createToken("test", "test");
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @Test
    void testCreateToken() throws NoSuchAlgorithmException {
        User user = new User();
        user.setId(1L);

        UserToken userToken = new UserToken();
        userToken.setName("TOKEN");

        Mockito.when(this.userDAO.load(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(this.userTokenDAO.find(Mockito.any())).thenReturn(List.of(userToken));
        Mockito.when(this.userTokenDAO.load(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(this.userTokenDAO.save(Mockito.any())).thenReturn(new UserToken());

        GlobalException exception = null;
        try {
            this.service.createToken("test", "test");
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
    }

    @Test
    void testGenerateToken() throws NoSuchAlgorithmException {
        UserToken userToken = new UserToken();
        userToken.setUser(new User());
        Mockito.when(this.userTokenDAO.load(Mockito.any())).thenReturn(Optional.of(userToken), Optional.empty());

        String token = this.service.generateToken();
        assertNotNull(token);
        assertEquals(Constants.TOKEN_LENGHT, token.length());
    }

    @Test
    void testDeleteEmptyToken() {
        GlobalException exception = null;
        try {
            this.service.deleteToken("test", null);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @Test
    void testDeleteTokenUnknownUser() {
        Mockito.when(this.userDAO.load(Mockito.any())).thenReturn(Optional.empty());

        GlobalException exception = null;
        try {
            SearchUserTokenDTO dto = new SearchUserTokenDTO();
            dto.setName("token");
            this.service.deleteToken("test", "token");
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @Test
    void testDeleteUnknownToken() {
        User user = new User();
        user.setId(1L);
        Mockito.when(this.userDAO.load(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(this.userTokenDAO.load(Mockito.any())).thenReturn(Optional.empty());

        GlobalException exception = null;
        try {
            SearchUserTokenDTO dto = new SearchUserTokenDTO();
            dto.setName("token");
            this.service.deleteToken("test", "token");
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @Test
    void testDeleteToken() {
        User user = new User();
        user.setId(1L);
        Mockito.when(this.userDAO.load(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(this.userTokenDAO.load(Mockito.any())).thenReturn(Optional.of(new UserToken()));
        Mockito.doNothing().when(this.userTokenDAO).delete(Mockito.any());

        GlobalException exception = null;
        try {
            SearchUserTokenDTO dto = new SearchUserTokenDTO();
            dto.setName("token");
            this.service.deleteToken("test", "token");
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
    }
}
