package com.octo.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

/**
 * Group entity.
 *
 * @author Vincent Moittié
 *
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "groups")
public @Data class Group extends AbstractEntity {
    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groups_seq")
    @SequenceGenerator(name = "groups_seq", sequenceName = "groups_grp_id_seq", allocationSize = 1)
    @Column(name = "grp_id", updatable = false, nullable = false)
    private Long id;
    /**
     * Master's project.
     */
    @ManyToOne()
    @JoinColumn(name = "pro_id")
    private Project masterProject;

    /**
     * Set insertDate before persist in repository.
     */
    @PrePersist
    public void prePersist() {
        this.setInsertDate(Timestamp.valueOf(LocalDateTime.now()));
    }
}
