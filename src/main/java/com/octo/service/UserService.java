package com.octo.service;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.octo.dao.IDAO;
import com.octo.model.authentication.UserRoleType;
import com.octo.model.dto.user.SearchUserDTO;
import com.octo.model.dto.user.UserViewDTO;
import com.octo.model.entity.User;
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
}
