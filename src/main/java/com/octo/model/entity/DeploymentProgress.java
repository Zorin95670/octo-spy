package com.octo.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Progress of deployment entity.
 *
 * @author Vincent Moittié
 *
 */
@Entity
@Table(name = "deployment_progress")
public class DeploymentProgress extends AbstractEntity implements IPrePersistance {

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deployment_progress_seq")
    @SequenceGenerator(name = "deployment_progress_seq", sequenceName = "deployment_progress_dpg_id_seq",
            allocationSize = 1)
    @Column(name = "dpg_id", updatable = false, nullable = false)
    private Long id;
    /**
     * Deployment's environment.
     */
    @OneToOne()
    @JoinColumn(name = "dpl_id")
    private Deployment deployment;

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
     * Get deployment.
     *
     * @return Deployment.
     */
    public Deployment getDeployment() {
        return deployment;
    }

    /**
     * Set deployment.
     *
     * @param deployment
     *            Deployment.
     */
    public void setDeployment(final Deployment deployment) {
        this.deployment = deployment;
    }
}
