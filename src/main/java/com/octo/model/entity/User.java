package com.octo.model.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.octo.utils.Constants;

/**
 * User entity.
 */
@Entity
@Table(name = "users")
public class User extends AbstractEntity {

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "users_usr_id_seq", allocationSize = 1)
    @Column(name = "usr_id", updatable = false, nullable = false)
    private Long id;
    /**
     * Type of authentication.
     */
    @Column(name = "authentication_type", length = Constants.DEFAULT_SIZE_OF_STRING)
    private String authenticationType;
    /**
     * Login.
     */
    @Column(name = "login", length = Constants.LONG_SIZE_OF_STRING)
    private String login;
    /**
     * First name.
     */
    @Column(name = "firstname", length = Constants.LONG_SIZE_OF_STRING)
    private String firstname;
    /**
     * Last name.
     */
    @Column(name = "lastname", length = Constants.LONG_SIZE_OF_STRING)
    private String lastname;
    /**
     * Password.
     */
    @Column(name = "password")
    private String password;
    /**
     * Email.
     */
    @Column(name = "email")
    private String email;
    /**
     * Active.
     */
    @Column(name = "active")
    private boolean active;

    /**
     * Set insertDate before persist in repository.
     */
    @PrePersist
    public void prePersist() {
        this.setInsertDate(Timestamp.valueOf(LocalDateTime.now()));
    }

    /**
     * Get id.
     *
     * @return Id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id.
     *
     * @param id
     *            Id.
     */
    public void setId(final Long id) {
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
     * Get user's password. Hashed with bcrypt and bf.
     *
     * @return User's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set user's password.
     *
     * @param password
     *            User's password.
     */
    public void setPassword(final String password) {
        this.password = password;
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
}
