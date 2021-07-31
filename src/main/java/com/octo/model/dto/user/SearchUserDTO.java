package com.octo.model.dto.user;

import com.octo.utils.predicate.filter.FilterType;
import com.octo.utils.predicate.filter.FilterType.Type;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * DTO to search user.
 */
public class SearchUserDTO extends QueryFilter {
    /**
     * Primary key.
     */
    @FilterType(type = Type.NUMBER)
    private String id;
    /**
     * Type of authentication.
     */
    @FilterType(type = Type.TEXT)
    private String authenticationType;
    /**
     * Login.
     */
    @FilterType(type = Type.TEXT)
    private String login;
    /**
     * First name.
     */
    @FilterType(type = Type.TEXT)
    private String firstname;
    /**
     * Last name.
     */
    @FilterType(type = Type.TEXT)
    private String lastname;
    /**
     * Email.
     */
    @FilterType(type = Type.TEXT)
    private String email;
    /**
     * Active.
     */
    @FilterType(type = Type.BOOLEAN)
    private String active;

    /**
     * Get id.
     *
     * @return Id.
     */
    public String getId() {
        return id;
    }

    /**
     * Set id.
     *
     * @param id
     *            Id.
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Get type of authentication.
     *
     * @return Type of authentication.
     */
    public String getAuthenticationType() {
        return authenticationType;
    }

    /**
     * Set type of authentication.
     *
     * @param authenticationType
     *            Type of authentication.
     */
    public void setAuthenticationType(final String authenticationType) {
        this.authenticationType = authenticationType;
    }

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
     * Is user's active.
     *
     * @return Active state.
     */
    public String getActive() {
        return active;
    }

    /**
     * Set user's active state.
     *
     * @param active
     *            Active state.
     */
    public void setActive(final String active) {
        this.active = active;
    }
}
