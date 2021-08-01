package com.octo.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

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
     * @param password
     *            Password.
     */
    public void updateDefaultAdminitratorPassword(final String password) {
        SearchUserDTO filter = new SearchUserDTO();
        filter.setLogin(Constants.DEFAULT_ADMIN_LOGIN);
        Optional<User> opt = userDAO.loadWithLock(filter);
        if (!opt.isPresent()) {
            throw new GlobalException(ErrorType.INTERNAL_ERROR, "No default admin account.");
        }
        User user = opt.get();

        user.setPassword(passwordEncoder.encode(password));

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
     * Indicate if active default administrator has not the default password.
     *
     * @return True if default password not match current administrator
     *         password.
     */
    public boolean isSecureAdministrator() {
        SearchUserDTO filter = new SearchUserDTO();
        filter.setLogin(Constants.DEFAULT_ADMIN_LOGIN);
        Optional<User> opt = userDAO.load(filter);
        if (!opt.isPresent()) {
            return true;
        }
        User user = opt.get();

        return !user.isActive() || !passwordEncoder.matches(Constants.DEFAULT_ADMIN_LOGIN, user.getPassword());
    }

    /**
     * Indicate if administrator email is different than the default.
     *
     * @return True if administrator has not the default email.
     */
    public boolean isValidAdministratorEmail() {
        SearchUserDTO filter = new SearchUserDTO();
        filter.setLogin(Constants.DEFAULT_ADMIN_LOGIN);
        Optional<User> opt = userDAO.load(filter);
        if (!opt.isPresent()) {
            return true;
        }
        User user = opt.get();
        return !user.isActive() || !"no-reply@change.it".equals(user.getEmail());
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
            return Collections.singletonList(UserRoleType.ADMIN);
        }
        return Collections.emptyList();
    }
}
