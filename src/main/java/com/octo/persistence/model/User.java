package com.octo.persistence.model;

import com.octo.model.common.Constants;
import com.octo.persistence.specification.filter.FilterType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * User entity.
 */
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
@Data
public class User extends AbstractEntity {

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "users_usr_id_seq", allocationSize = 1)
    @Column(name = "usr_id", updatable = false, nullable = false)
    @FilterType(type = FilterType.Type.NUMBER)
    private Long id;
    /**
     * Type of authentication.
     */
    @Column(name = "authentication_type", length = Constants.DEFAULT_SIZE_OF_STRING)
    @FilterType(type = FilterType.Type.TEXT)
    private String authenticationType;
    /**
     * Login.
     */
    @Column(name = "login")
    @FilterType(type = FilterType.Type.TEXT)
    private String login;
    /**
     * First name.
     */
    @Column(name = "firstname")
    @FilterType(type = FilterType.Type.TEXT)
    private String firstname;
    /**
     * Last name.
     */
    @Column(name = "lastname")
    @FilterType(type = FilterType.Type.TEXT)
    private String lastname;
    /**
     * Password.
     */
    @Column(name = "password")
    @FilterType(type = FilterType.Type.TEXT)
    private String password;
    /**
     * Email.
     */
    @Column(name = "email")
    @FilterType(type = FilterType.Type.TEXT)
    private String email;
    /**
     * Active.
     */
    @Column(name = "active")
    @FilterType(type = FilterType.Type.BOOLEAN)
    private boolean active;

    /**
     * Set insertDate before persist in repository.
     */
    @PrePersist
    public void prePersist() {
        this.setInsertDate(Timestamp.valueOf(LocalDateTime.now()));
    }
}
