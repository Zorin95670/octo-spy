package com.octo.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Deployment view entity.
 *
 * @author vmoittie
 *
 */
@Entity
@Table(name = "deployments_view")
public class DeploymentView {

    /**
     * Primary key.
     */
    @Id
    @Column(name = "dpl_id", insertable = false, updatable = false, nullable = false)
    private Long id;
    /**
     * Deployment's environment.
     */
    @Column(name = "environment", insertable = false, updatable = false)
    private String environment;
    /**
     * Deployment's project.
     */
    @Column(name = "project", insertable = false, updatable = false)
    private String project;
    /**
     * Deployed version.
     */
    @Column(name = "version", insertable = false, updatable = false)
    private String version;
    /**
     * Client.
     */
    @Column(name = "client", insertable = false, updatable = false)
    private String client;

    /**
     * Get deployment id.
     *
     * @return Deployment id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set deployment id.
     *
     * @param id
     *            Deployment id.
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Get environment name.
     *
     * @return Environment name.
     */
    public String getEnvironment() {
        return environment;
    }

    /**
     * Set environment name.
     *
     * @param environment
     *            Environment name.
     */
    public void setEnvironment(final String environment) {
        this.environment = environment;
    }

    /**
     * Get project name.
     *
     * @return Project name.
     */
    public String getProject() {
        return project;
    }

    /**
     * Set project name.
     *
     * @param project
     *            Project name.
     */
    public void setProject(final String project) {
        this.project = project;
    }

    /**
     * Get version number.
     *
     * @return Version number.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Set version number.
     *
     * @param version
     *            Version number.
     */
    public void setVersion(final String version) {
        this.version = version;
    }

    /**
     * Get version name.
     *
     * @return Client name.
     */
    public String getClient() {
        return client;
    }

    /**
     * Set version name.
     *
     * @param client
     *            Client name.
     */
    public void setClient(final String client) {
        this.client = client;
    }

}
