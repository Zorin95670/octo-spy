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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Progress of deployment entity.
 *
 * @author Vincent Moittié
 */
@Entity
@Table(name = "deployment_progress")
@EqualsAndHashCode(callSuper = true)
@Data
public class DeploymentProgress extends AbstractEntity {

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deployment_progress_seq")
    @SequenceGenerator(name = "deployment_progress_seq", sequenceName = "deployment_progress_dpg_id_seq",
            allocationSize = 1)
    @Column(name = "dpg_id", updatable = false, nullable = false)
    @FilterType(type = FilterType.Type.NUMBER)
    private Long id;
    /**
     * Deployment's environment.
     */
    @OneToOne()
    @JoinColumn(name = "dpl_id")
    @FilterType(type = FilterType.Type.NUMBER)
    private Deployment deployment;

    /**
     * Set insertDate before persist in repository.
     */
    @PrePersist
    public void prePersist() {
        this.setInsertDate(Timestamp.valueOf(LocalDateTime.now()));
    }
}
