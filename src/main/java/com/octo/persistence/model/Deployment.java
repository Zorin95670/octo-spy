package com.octo.persistence.model;

import com.octo.model.common.Constants;
import com.octo.persistence.specification.filter.FilterType;
import com.octo.persistence.specification.filter.FilterType.Type;
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
 * Deployment entity.
 *
 * @author Vincent Moittié
 */
@Entity
@Table(name = "deployments")
@EqualsAndHashCode(callSuper = true)
@Data
public class Deployment extends AbstractEntity {

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deployments_seq")
    @SequenceGenerator(name = "deployments_seq", sequenceName = "deployments_dpl_id_seq", allocationSize = 1)
    @Column(name = "dpl_id", updatable = false, nullable = false)
    @FilterType(type = Type.NUMBER)
    private Long id;
    /**
     * Deployment's environment.
     */
    @ManyToOne()
    @JoinColumn(name = "env_id")
    @FilterType(type = Type.NUMBER)
    private Environment environment;
    /**
     * Deployment's project.
     */
    @ManyToOne()
    @JoinColumn(name = "pro_id")
    @FilterType(type = Type.NUMBER)
    private Project project;
    /**
     * Deployed version.
     */
    @Column(name = "version", nullable = false, length = Constants.DEFAULT_SIZE_OF_STRING)
    @FilterType(type = Type.TEXT)
    private String version;
    /**
     * Client.
     */
    @Column(name = "client", length = Constants.DEFAULT_SIZE_OF_STRING)
    @FilterType(type = Type.TEXT)
    private String client;
    /**
     * Is deployment is still alive.
     */
    @Column(name = "alive")
    @FilterType(type = Type.BOOLEAN)
    private boolean alive;

    /**
     * Set insertDate before persist in repository.
     */
    @PrePersist
    public void prePersist() {
        this.setInsertDate(Timestamp.valueOf(LocalDateTime.now()));
    }
}
