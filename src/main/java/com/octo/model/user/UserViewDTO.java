package com.octo.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

/**
 * User view DTO.
 *
 * @author Vincent Moittié
 */
@Data
public class UserViewDTO {

    /**
     * Login.
     */
    private String login;
    /**
     * First name.
     */
    private String firstname;
    /**
     * Last name.
     */
    private String lastname;
    /**
     * Email.
     */
    private String email;
    /**
     * Active.
     */
    private boolean active;
    /**
     * The creation date of this row.
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp insertDate;
    /**
     * The last update date of this row.
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp updateDate;
}
