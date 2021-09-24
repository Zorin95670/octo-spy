package com.octo.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.octo.dao.IDAO;
import com.octo.model.authentication.UserRoleType;
import com.octo.model.dto.user.SearchUserDTO;
import com.octo.model.dto.user.UserViewDTO;
import com.octo.model.dto.user.token.SearchUserTokenDTO;
import com.octo.model.entity.User;
import com.octo.model.entity.UserToken;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.utils.Constants;
import com.octo.utils.bean.BeanMapper;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * User service.
 */
@Service
@Transactional
public class UserService {

    /**
     * User DAO.
     */
    @Autowired
    private IDAO<User, QueryFilter> userDAO;

    /**
     * User DAO.
     */
    @Autowired
    private IDAO<UserToken, QueryFilter> userTokenDAO;

    /**
     * Password encoder.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Update administrator password.
     *
     * @param encodedPassword
     *            Base64 encoded password.
     */
    public void updateDefaultAdminitratorPassword(final String encodedPassword) {
        if (StringUtils.isBlank(encodedPassword)) {
            throw new GlobalException(ErrorType.EMPTY_VALUE, "password");
        }
        String password = new String(Base64.getDecoder().decode(encodedPassword.getBytes()));
        if (StringUtils.length(password) < Constants.MINIMUM_PASSWORD_LENGTH
                || StringUtils.length(password) > Constants.MAXMUM_PASSWORD_LENGTH) {
            throw new GlobalException(ErrorType.WRONG_VALUE, "Password length.");
        }
        SearchUserDTO filter = new SearchUserDTO();
        filter.setLogin(Constants.DEFAULT_ADMIN_LOGIN);
        Optional<User> opt = userDAO.loadWithLock(filter);
        if (opt.isEmpty()) {
            throw new GlobalException(ErrorType.INTERNAL_ERROR, "No default admin account.");
        }
        User user = opt.get();

        user.setPassword(passwordEncoder.encode(password));

        userDAO.save(user);
    }

    /**
     * Update administrator email.
     *
     * @param email
     *            Email.
     */
    public void updateDefaultAdminitratorEmail(final String email) {
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new GlobalException(ErrorType.WRONG_VALUE, "Email.");
        }
        SearchUserDTO filter = new SearchUserDTO();
        filter.setLogin(Constants.DEFAULT_ADMIN_LOGIN);
        Optional<User> opt = userDAO.loadWithLock(filter);
        if (opt.isEmpty()) {
            throw new GlobalException(ErrorType.INTERNAL_ERROR, "No default admin account.");
        }
        User user = opt.get();

        user.setEmail(email);

        userDAO.save(user);
    }

    /**
     * Indicate if default administrator can be authenticate with this password.
     *
     * @param password
     *            Password to check.
     * @return True if default admin is active and password is the good one.
     */
    public boolean isDefaultAdminitratorAllowed(final String password) {
        SearchUserDTO filter = new SearchUserDTO();
        filter.setLogin(Constants.DEFAULT_ADMIN_LOGIN);
        Optional<User> opt = userDAO.load(filter);
        if (!opt.isPresent()) {
            return false;
        }
        User user = opt.get();

        return user.isActive() && passwordEncoder.matches(password, user.getPassword());
    }

    /**
     * Get user from login.
     *
     * @param login
     *            Login.
     * @return User.
     */
    public UserViewDTO getUser(final String login) {
        SearchUserDTO filter = new SearchUserDTO();
        filter.setLogin(login);
        Optional<User> opt = userDAO.load(filter);
        if (!opt.isPresent()) {
            return null;
        }
        return new BeanMapper<>(UserViewDTO.class).apply(opt.get());

    }

    /**
     * Get user roles from login.
     *
     * @param login
     *            Login.
     * @return User roles.
     */
    public List<String> getUserRoles(final String login) {
        if (Constants.DEFAULT_ADMIN_LOGIN.equals(login)) {
            return List.of(UserRoleType.ADMIN);
        }
        return Collections.emptyList();
    }

    /**
     * Get user from token.
     *
     * @param token
     *            Token to find user.
     * @return User or null.
     */
    public User getUserFromToken(final String token) {
        Optional<UserToken> userToken = this.userTokenDAO.load(new SearchUserTokenDTO(token));
        if (userToken.isEmpty()) {
            return null;
        }
        return userToken.get().getUser();
    }

    /**
     * Get user tokens.
     *
     * @param login
     *            User login.
     * @return Tokens.
     */
    public List<String> getUserToken(final String login) {
        SearchUserDTO userFilter = new SearchUserDTO();
        userFilter.setLogin(login);
        Optional<User> user = userDAO.load(userFilter);
        if (user.isEmpty()) {
            throw new GlobalException(ErrorType.ENTITY_NOT_FOUND, "user", login);
        }

        SearchUserTokenDTO tokenFilter = new SearchUserTokenDTO();
        tokenFilter.setUser(user.get().getId().toString());

        return userTokenDAO.find(tokenFilter).stream().map(UserToken::getName).toList();
    }

    /**
     * Create token for user.
     *
     * @param login
     *            User login.
     * @param name
     *            Token name.
     * @return Generated token.
     * @throws NoSuchAlgorithmException
     *             On no secure random algorithm found.
     */
    public String createToken(final String login, final String name) throws NoSuchAlgorithmException {
        SearchUserDTO userFilter = new SearchUserDTO();
        userFilter.setLogin(login);
        Optional<User> user = userDAO.load(userFilter);
        if (user.isEmpty()) {
            throw new GlobalException(ErrorType.ENTITY_NOT_FOUND, "user", login);
        }

        List<String> tokens = this.getUserToken(login);
        String tokenName = StringUtils.upperCase(name);
        if (tokens.contains(tokenName)) {
            throw new GlobalException(ErrorType.WRONG_VALUE, "name", "duplicate");
        }

        String token = this.generateToken();
        UserToken userToken = new UserToken();
        userToken.setUser(user.get());
        userToken.setName(tokenName);
        userToken.setToken(passwordEncoder.encode(token));

        this.userTokenDAO.save(userToken);

        return token;
    }

    /**
     * Generate token.
     *
     * @return Token.
     * @throws NoSuchAlgorithmException
     *             On no secure random algorithm found.
     */
    public String generateToken() throws NoSuchAlgorithmException {
        String token = RandomStringUtils.random(Constants.TOKEN_LENGHT, 0, 0, true, true, null,
                SecureRandom.getInstanceStrong());
        User user = this.getUserFromToken(token);

        if (user == null) {
            return token;
        }
        return this.generateToken();
    }

    /**
     * Delete user token.
     *
     * @param login
     *            User login.
     * @param filter
     *            Filter to find token name.
     */
    public void deleteToken(final String login, final SearchUserTokenDTO filter) {
        if (StringUtils.isBlank(filter.getName())) {
            throw new GlobalException(ErrorType.EMPTY_VALUE, "name");
        }
        SearchUserDTO userFilter = new SearchUserDTO();
        userFilter.setLogin(login);
        Optional<User> user = userDAO.load(userFilter);
        if (user.isEmpty()) {
            throw new GlobalException(ErrorType.ENTITY_NOT_FOUND, "user", login);
        }

        SearchUserTokenDTO tokenFilter = new SearchUserTokenDTO();
        tokenFilter.setUser(user.get().getId().toString());
        tokenFilter.setName(filter.getName());

        Optional<UserToken> token = userTokenDAO.load(tokenFilter);

        if (token.isEmpty()) {
            throw new GlobalException(ErrorType.ENTITY_NOT_FOUND, "user token");
        }

        userTokenDAO.delete(token.get());
    }
}
