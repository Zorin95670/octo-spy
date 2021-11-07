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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * User entity.
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "users")
public @Data class User extends AbstractEntity {

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
}
