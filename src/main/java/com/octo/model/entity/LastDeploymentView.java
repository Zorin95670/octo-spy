package com.octo.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity of last deployment.
 *
 * @author Vincent Moittié
 *
 */
@Entity
@Table(name = "last_deployments_view")
public class LastDeploymentView extends AbstractDeploymentView {

    /**
     * Is deployment on master project.
     */
    @Column(name = "on_master_project")
    private boolean onMasterProject;

    /**
     * Deployment on master project.
     *
     * @return On master project.
     */
    public boolean getOnMasterProject() {
        return onMasterProject;
    }

    /**
     * Set deployment on master project.
     *
     * @param alive
     *            On master project.
     */
    public void setAlive(final boolean alive) {
        this.onMasterProject = alive;
    }
}
