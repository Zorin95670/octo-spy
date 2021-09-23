package com.octo.model.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.octo.utils.Constants;

/**
 * User token entity.
 */
@Entity
@Table(name = "user_tokens")
public class UserToken extends AbstractEntity {

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_tokens_seq")
    @SequenceGenerator(name = "user_tokens_seq", sequenceName = "user_tokens_ust_id_seq", allocationSize = 1)
    @Column(name = "ust_id", updatable = false, nullable = false)
    private Long id;
    /**
     * User.
     */
    @ManyToOne()
    @JoinColumn(name = "usr_id")
    private User user;
    /**
     * Token name.
     */
    @Column(name = "name", nullable = false, length = Constants.LONG_SIZE_OF_STRING)
    private String name;
    /**
     * Token value.
     */
    @Column(name = "token", nullable = false)
    private String token;

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
     * Get user.
     *
     * @return User.
     */
    public User getUser() {
        return user;
    }

    /**
     * Set user.
     *
     * @param user
     *            User.
     */
    public void setUser(final User user) {
        this.user = user;
    }

    /**
     * Get token name.
     *
     * @return Token name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set token name.
     *
     * @param name
     *            Token name.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Get token token.
     *
     * @return Token token.
     */
    public String getToken() {
        return token;
    }

    /**
     * Set token token.
     *
     * @param token
     *            Token token.
     */
    public void setToken(final String token) {
        this.token = token;
    }

}
