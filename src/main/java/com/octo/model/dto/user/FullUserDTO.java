package com.octo.model.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * DTO with user and associated roles.
 *
 * @author Vincent Moittié
 *
 */
public @Data class FullUserDTO {

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
}
