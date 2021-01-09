package com.octo.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "deployments_view")
public class DeploymentView extends AbstractDeploymentView {
    /**
     * Is deployment is sill alive.
     */
    @Column(name = "alive")
    private boolean alive;

    /**
     * Is deployment alive.
     *
     * @return Alive state.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Set deployement alive state.
     *
     * @param alive
     *            Alive state.
     */
    public void setAlive(final boolean alive) {
        this.alive = alive;
    }

}
