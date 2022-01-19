package com.octo.service;

import com.octo.model.user.UserViewDTO;
import com.octo.persistence.model.User;
import org.springframework.data.domain.Page;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * User service.
 */
public interface UserService extends ServiceHelper {
    /**
     * Update administrator password.
     *
     * @param encodedPassword Base64 encoded password.
     */
    void updateDefaultAdministratorPassword(String encodedPassword);

    /**
     * Update administrator email.
     *
     * @param email Email.
     */
    void updateDefaultAdministratorEmail(String email);

    /**
     * Indicate if default administrator can be authenticated with this password.
     *
     * @param password Password to check.
     * @return True if default admin is active and password is the good one.
     */
    boolean isDefaultAdministratorAllowed(String password);

    /**
     * Get user from login.
     *
     * @param login Login.
     * @return User.
     */
    UserViewDTO getUser(String login);

    /**
     * Get user roles from login.
     *
     * @param login Login.
     * @return User roles.
     */
    List<String> getUserRoles(String login);

    /**
     * Get user from token.
     *
     * @param token Token to find user.
     * @return User or null.
     */
    User getUserFromToken(String token);

    /**
     * Get user tokens.
     *
     * @param login User login.
     * @return Tokens.
     */
    Page<String> getUserToken(String login);

    /**
     * Create token for user.
     *
     * @param login User login.
     * @param name  Token name.
     * @return Generated token.
     * @throws NoSuchAlgorithmException On no secure random algorithm found.
     */
    String createToken(String login, String name) throws NoSuchAlgorithmException;

    /**
     * Generate token.
     *
     * @return Token.
     * @throws NoSuchAlgorithmException On no secure random algorithm found.
     */
    String generateToken() throws NoSuchAlgorithmException;

    /**
     * Delete user token.
     *
     * @param login User login.
     * @param name  Token name.
     */
    void deleteToken(String login, String name);
}
