package com.octo.model.dto.user;

import java.util.Collections;
import java.util.List;

/**
 * DTO with user and associated roles.
 *
 * @author Vincent Moittié
 *
 */
public class FullUserDTO {

    /**
     * User.
     */
    private UserViewDTO user;

    /**
     * User roles.
     */
    private List<String> roles;

    /**
     * Default constructor.
     */
    public FullUserDTO() {
        this.setRoles(Collections.emptyList());
    }

    /**
     * Get user.
     *
     * @return User.
     */
    public UserViewDTO getUser() {
        return user;
    }

    /**
     * Set user.
     *
     * @param user
     *            User.
     */
    public void setUser(final UserViewDTO user) {
        this.user = user;
    }

    /**
     * Get roles.
     *
     * @return Roles.
     */
    public List<String> getRoles() {
        return roles;
    }

    /**
     * Set roles.
     *
     * @param roles
     *            Roles.
     */
    public void setRoles(final List<String> roles) {
        this.roles = roles;
    }
}
