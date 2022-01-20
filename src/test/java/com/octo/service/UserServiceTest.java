package com.octo.service;

import com.octo.model.common.Constants;
import com.octo.model.common.UserRoleType;
import com.octo.model.error.GlobalException;
import com.octo.persistence.model.User;
import com.octo.persistence.model.UserToken;
import com.octo.persistence.repository.UserRepository;
import com.octo.persistence.repository.UserTokenRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.CharBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserTokenRepository userTokenRepository;

    @Mock
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @InjectMocks
    UserServiceImpl service;

    @Test
    void testUpdateDefaultAdministratorPasswordWithBadLength() {
        String tooShortPassword = CharBuffer.allocate(7).toString().replace('\0', 'a');
        String tooLongPassword = CharBuffer.allocate(51).toString().replace('\0', 'a');

        GlobalException exception = null;
        try {
            service.updateDefaultAdministratorPassword("");
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);

        exception = null;
        try {
            String encodedPassword = Base64.getUrlEncoder().encodeToString(tooShortPassword.getBytes());
            service.updateDefaultAdministratorPassword(encodedPassword);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        exception = null;
        try {
            String encodedPassword = Base64.getUrlEncoder().encodeToString(tooLongPassword.getBytes());
            service.updateDefaultAdministratorPassword(encodedPassword);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @Test
    void testUpdateDefaultAdministratorPassword() {
        Mockito.when(passwordEncoder.encode(Mockito.any())).thenReturn(null);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(null);
        Mockito.when(userRepository.findByLogin(Mockito.any())).thenReturn(Optional.empty());

        GlobalException exception = null;
        try {
            service.updateDefaultAdministratorPassword("password1234");
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);

        Mockito.when(userRepository.findByLogin(Mockito.any())).thenReturn(Optional.of(new User()));

        exception = null;
        try {
            service.updateDefaultAdministratorPassword("password1234");
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
    }

    @Test
    void testUpdateDefaultAdministratorEmail() {
        GlobalException exception = null;
        try {
            service.updateDefaultAdministratorEmail("bad email");
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(null);
        Mockito.when(userRepository.findByLogin(Mockito.any())).thenReturn(Optional.empty());
        String validEmail = "testemail@test.com";
        exception = null;
        try {
            service.updateDefaultAdministratorEmail(validEmail);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);

        Mockito.when(userRepository.findByLogin(Mockito.any())).thenReturn(Optional.of(new User()));
        exception = null;
        try {
            service.updateDefaultAdministratorEmail(validEmail);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
    }

    @Test
    void testIsDefaultAdministratorAllowed() {
        Mockito.when(userRepository.findByLogin(Mockito.any())).thenReturn(Optional.empty());
        assertFalse(service.isDefaultAdministratorAllowed("password"));

        User user = new User();
        user.setActive(false);
        Mockito.when(userRepository.findByLogin(Mockito.any())).thenReturn(Optional.of(user));
        assertFalse(service.isDefaultAdministratorAllowed("password"));

        user.setActive(true);
        Mockito.when(userRepository.findByLogin(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches(Mockito.any(), Mockito.any())).thenReturn(false);
        assertFalse(service.isDefaultAdministratorAllowed("password"));

        Mockito.when(passwordEncoder.matches(Mockito.any(), Mockito.any())).thenReturn(true);
        assertTrue(service.isDefaultAdministratorAllowed("password"));
    }

    @Test
    void testGetUser() {
        Mockito.when(userRepository.findByLogin(Mockito.any())).thenReturn(Optional.empty());
        assertNull(service.getUser("test"));

        Mockito.when(userRepository.findByLogin(Mockito.any())).thenReturn(Optional.of(new User()));
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
        Mockito.when(this.userTokenRepository.findOne(Mockito.any())).thenReturn(Optional.of(token));
        assertNotNull(service.getUserFromToken("token"));

        Mockito.when(this.userTokenRepository.findOne(Mockito.any())).thenReturn(Optional.empty());
        assertNull(service.getUserFromToken("token"));
    }

    @Test
    void testGetUserTokenUnknownUser() {
        Mockito.when(this.userRepository.findByLogin(Mockito.any())).thenReturn(Optional.empty());
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
        Mockito.when(this.userRepository.findByLogin(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(this.userTokenRepository.findByUserId(Mockito.any(), Mockito.any()))
                .thenReturn(new PageImpl<>(tokens));

        assertEquals(List.of("token1", "token2"), service.getUserToken("admin").stream().toList());
    }

    @Test
    void testCreateDuplicateToken() throws NoSuchAlgorithmException {
        User user = new User();
        user.setId(1L);

        UserToken userToken = new UserToken();
        userToken.setName("TOKEN");

        Mockito.when(this.userRepository.findByLogin(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(this.userTokenRepository.findByUserIdAndName(Mockito.any(), Mockito.any()))
                .thenReturn(Optional.of(userToken));

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
        Mockito.when(this.userRepository.findByLogin(Mockito.any())).thenReturn(Optional.empty());

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

        Mockito.when(this.userRepository.findByLogin(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(this.userTokenRepository.findByUserIdAndName(Mockito.any(), Mockito.any()))
                .thenReturn(Optional.empty());
        Mockito.when(this.userTokenRepository.findOne(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(this.userTokenRepository.save(Mockito.any())).thenReturn(new UserToken());

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
        Mockito.when(this.userTokenRepository.findOne(Mockito.any())).thenReturn(Optional.of(userToken),
                Optional.empty());

        String token = this.service.generateToken();
        assertNotNull(token);
        assertEquals(Constants.TOKEN_LENGTH, token.length());
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
        Mockito.when(this.userRepository.findByLogin(Mockito.any())).thenReturn(Optional.empty());

        GlobalException exception = null;
        try {
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
        Mockito.when(this.userRepository.findByLogin(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(this.userTokenRepository.findByUserIdAndName(Mockito.any(), Mockito.any()))
                .thenReturn(Optional.empty());

        GlobalException exception = null;
        try {
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
        Mockito.when(this.userRepository.findByLogin(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(this.userTokenRepository.findByUserIdAndName(Mockito.any(), Mockito.any()))
                .thenReturn(Optional.of(new UserToken()));
        Mockito.doNothing().when(this.userTokenRepository).delete(Mockito.any());

        GlobalException exception = null;
        try {
            this.service.deleteToken("test", "token");
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
    }
}
