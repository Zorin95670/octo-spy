package com.octo.model.dto.deployment;

import com.octo.model.dto.common.DefaultDTO;

/**
 * DTO to call on create deployment.
 *
 * @author vmoittie
 *
 */
public class NewDeploymentDTO extends DefaultDTO {

    /**
     * Deployment's environment.
     */
    private String environment;

    /**
     * Deployment's project.
     */
    private String project;
    /**
     * Deployed version.
     */
    private String version;
    /**
     * Client.
     */
    private String client;
    /**
     * Is deployment is sill alive.
     */
    private boolean alive;

    /**
     * Get environment's name.
     *
     * @return The environment's name.
     */
    public String getEnvironment() {
        return environment;
    }

    /**
     * Set the environment's name.
     *
     * @param environment
     *            The environment's name to set.
     */
    public void setEnvironment(final String environment) {
        this.environment = environment;
    }

    /**
     * Get project's name.
     *
     * @return The project's name.
     */
    public String getProject() {
        return project;
    }

    /**
     * Set the project's name.
     *
     * @param project
     *            The project's name to set.
     */
    public void setProject(final String project) {
        this.project = project;
    }

    /**
     * Get version.
     *
     * @return The version.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Set version.
     *
     * @param version
     *            The version to set.
     */
    public void setVersion(final String version) {
        this.version = version;
    }

    /**
     * Get client.
     *
     * @return The client.
     */
    public String getClient() {
        return client;
    }

    /**
     * Set client.
     *
     * @param client
     *            The client to set.
     */
    public void setClient(final String client) {
        this.client = client;
    }

    /**
     * Is deployment alive.
     *
     * @return The alive state.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Set deployment's alive state.
     *
     * @param alive
     *            The alive state to set.
     */
    public void setAlive(final boolean alive) {
        this.alive = alive;
    }
}
