package com.octo.model.user;

import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * DTO with user and associated roles.
 *
 * @author Vincent Moittié
 */
@Data
public class UserDTO {

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
    public UserDTO() {
        this.setRoles(Collections.emptyList());
    }
}
