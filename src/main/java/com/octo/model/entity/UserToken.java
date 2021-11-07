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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * User token entity.
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "user_tokens")
public @Data class UserToken extends AbstractEntity {

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
}
