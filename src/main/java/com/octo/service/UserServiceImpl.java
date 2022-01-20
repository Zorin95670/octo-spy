package com.octo.service;

import com.octo.model.common.Constants;
import com.octo.model.common.UserRoleType;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.model.user.UserViewDTO;
import com.octo.persistence.model.User;
import com.octo.persistence.model.UserToken;
import com.octo.persistence.repository.UserRepository;
import com.octo.persistence.repository.UserTokenRepository;
import com.octo.persistence.specification.SpecificationHelper;
import com.octo.utils.bean.BeanMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of user service.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    /**
     * User repository.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * User token repository.
     */
    @Autowired
    private UserTokenRepository userTokenRepository;

    /**
     * Password encoder.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void updateDefaultAdministratorPassword(final String encodedPassword) {
        String password = new String(Base64.getUrlDecoder().decode(encodedPassword.getBytes()));

        if (StringUtils.length(password) < Constants.MINIMUM_PASSWORD_LENGTH
                || StringUtils.length(password) > Constants.MAXIMUM_PASSWORD_LENGTH) {
            throw new GlobalException(ErrorType.WRONG_VALUE, "Password length.");
        }

        Optional<User> opt = userRepository.findByLogin(Constants.DEFAULT_ADMIN_LOGIN);
        if (opt.isEmpty()) {
            throw new GlobalException(ErrorType.INTERNAL_ERROR, "No default admin account.");
        }
        User user = opt.get();

        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
    }

    @Override
    public void updateDefaultAdministratorEmail(final String email) {
        Optional<User> opt = userRepository.findByLogin(Constants.DEFAULT_ADMIN_LOGIN);
        if (opt.isEmpty()) {
            throw new GlobalException(ErrorType.INTERNAL_ERROR, "No default admin account.");
        }
        User user = opt.get();

        user.setEmail(email);

        userRepository.save(user);
    }

    @Override
    public boolean isDefaultAdministratorAllowed(final String password) {
        Optional<User> opt = userRepository.findByLogin(Constants.DEFAULT_ADMIN_LOGIN);
        if (opt.isEmpty()) {
            return false;
        }
        User user = opt.get();

        return user.isActive() && passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public UserViewDTO getUser(final String login) {
        Optional<User> opt = userRepository.findByLogin(Constants.DEFAULT_ADMIN_LOGIN);
        return opt.map(user -> new BeanMapper<>(UserViewDTO.class).apply(user)).orElse(null);

    }

    @Override
    public List<String> getUserRoles(final String login) {
        if (Constants.DEFAULT_ADMIN_LOGIN.equals(login)) {
            return List.of(UserRoleType.ADMIN);
        }
        return Collections.emptyList();
    }

    @Override
    public User getUserFromToken(final String token) {
        Optional<UserToken> userToken = this.userTokenRepository.findOne(
                new SpecificationHelper<>(UserToken.class, Map.of("token", token)));
        if (userToken.isEmpty()) {
            return null;
        }
        return userToken.get().getUser();
    }

    @Override
    public Page<String> getUserToken(final String login) {
        Optional<User> user = userRepository.findByLogin(login);
        if (user.isEmpty()) {
            throw new GlobalException(ErrorType.ENTITY_NOT_FOUND, "user", login);
        }
        Pageable pageable = PageRequest.of(0, Constants.MAXIMUM_RESOURCE_SIZE, Sort.by(Sort.Direction.ASC, "name"));
        return userTokenRepository.findByUserId(user.get().getId(), pageable).map(UserToken::getName);
    }

    @Override
    public String createToken(final String login, final String name) throws NoSuchAlgorithmException {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new GlobalException(ErrorType.ENTITY_NOT_FOUND, "user", login));

        String tokenName = StringUtils.upperCase(name);
        Optional<UserToken> opt = this.userTokenRepository.findByUserIdAndName(user.getId(), tokenName);
        if (opt.isPresent()) {
            throw new GlobalException(ErrorType.WRONG_VALUE, "name", "duplicate");
        }

        String token = this.generateToken();
        UserToken userToken = new UserToken();
        userToken.setUser(user);
        userToken.setName(tokenName);
        userToken.setToken(passwordEncoder.encode(token));

        this.userTokenRepository.save(userToken);

        return token;
    }

    @Override
    public String generateToken() throws NoSuchAlgorithmException {
        String token = RandomStringUtils.random(Constants.TOKEN_LENGTH, 0, 0, true, true, null,
                SecureRandom.getInstanceStrong());
        User user = this.getUserFromToken(token);

        if (user == null) {
            return token;
        }
        return this.generateToken();
    }

    @Override
    public void deleteToken(final String login, final String name) {
        if (StringUtils.isBlank(name)) {
            throw new GlobalException(ErrorType.EMPTY_VALUE, "name");
        }
        Optional<User> user = userRepository.findByLogin(login);
        if (user.isEmpty()) {
            throw new GlobalException(ErrorType.ENTITY_NOT_FOUND, "user", login);
        }

        Optional<UserToken> token = userTokenRepository.findByUserIdAndName(user.get().getId(), name);

        if (token.isEmpty()) {
            throw new GlobalException(ErrorType.ENTITY_NOT_FOUND, "user token");
        }

        userTokenRepository.delete(token.get());
    }
}
