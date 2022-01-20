package com.octo.persistence.model;

import com.octo.persistence.specification.filter.FilterType;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * User token entity.
 */
@Entity
@Table(name = "user_tokens")
@EqualsAndHashCode(callSuper = true)
@Data
public class UserToken extends AbstractEntity {

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_tokens_seq")
    @SequenceGenerator(name = "user_tokens_seq", sequenceName = "user_tokens_ust_id_seq", allocationSize = 1)
    @Column(name = "ust_id", updatable = false, nullable = false)
    @FilterType(type = FilterType.Type.NUMBER)
    private Long id;
    /**
     * User.
     */
    @ManyToOne()
    @JoinColumn(name = "usr_id")
    @FilterType(type = FilterType.Type.NUMBER)
    private User user;
    /**
     * Token name.
     */
    @Column(name = "name", nullable = false)
    @FilterType(type = FilterType.Type.TEXT)
    private String name;
    /**
     * Token value.
     */
    @Column(name = "token", nullable = false)
    @FilterType(type = FilterType.Type.TOKEN)
    private String token;

    /**
     * Set insertDate before persist in repository.
     */
    @PrePersist
    public void prePersist() {
        this.setInsertDate(Timestamp.valueOf(LocalDateTime.now()));
    }
}
