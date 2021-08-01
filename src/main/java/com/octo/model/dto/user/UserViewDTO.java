package com.octo.model.dto.user;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * User view DTO.
 *
 * @author Vincent Moittié
 *
 */
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

    /**
     * Get login.
     *
     * @return Login.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Set login.
     *
     * @param login
     *            Login.
     */
    public void setLogin(final String login) {
        this.login = login;
    }

    /**
     * Get user's first name.
     *
     * @return User's first name.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Set user's first name.
     *
     * @param firstname
     *            User's first name.
     */
    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    /**
     * Get user's last name.
     *
     * @return User's last name.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Set user's last name.
     *
     * @param lastname
     *            User's last name.
     */
    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    /**
     * Get user's email.
     *
     * @return User's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set user's email.
     *
     * @param email
     *            User's email.
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * User can be used for authentication.
     *
     * @return State.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Enable or disabled this user for authentication.
     *
     * @param active
     *            State.
     */
    public void setActive(final boolean active) {
        this.active = active;
    }

    /**
     * Get the creation date of this entity.
     *
     * @return Creation date.
     */
    public Timestamp getInsertDate() {
        if (this.insertDate == null) {
            return null;
        }
        return Timestamp.valueOf(insertDate.toLocalDateTime());
    }

    /**
     * Set the creation date of this entity.
     *
     * @param insertDate
     *            Creation date.
     */
    public void setInsertDate(final Timestamp insertDate) {
        if (insertDate == null) {
            this.insertDate = null;
            return;
        }
        this.insertDate = Timestamp.valueOf(insertDate.toLocalDateTime());
    }

    /**
     * Get the last update date of this entity.
     *
     * @return Last update date.
     */
    public Timestamp getUpdateDate() {
        if (this.updateDate == null) {
            return null;
        }
        return Timestamp.from(updateDate.toInstant());
    }

    /**
     * Set the last update date of this entity.
     *
     * @param updateDate
     *            Last update date.
     */
    public void setUpdateDate(final Timestamp updateDate) {
        if (updateDate == null) {
            this.updateDate = null;
            return;
        }
        this.updateDate = Timestamp.from(updateDate.toInstant());
    }
}
