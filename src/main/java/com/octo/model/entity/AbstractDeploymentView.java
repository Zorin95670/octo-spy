package com.octo.model.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Default deployment view entity.
 *
 * @author Vincent Moittié
 *
 */
@MappedSuperclass
public abstract class AbstractDeploymentView {

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
     * Deployment's master project.
     */
    @Column(name = "master_project", insertable = false, updatable = false)
    private String masterProject;
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
     * Is in progress.
     */
    @Column(name = "in_progress", insertable = false, updatable = false)
    private boolean inProgress;
    /**
     * The creation date of this row.
     */
    @Column(name = "insert_date", updatable = false)
    private Timestamp insertDate;

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
     * Get master project name.
     *
     * @return Master project name.
     */
    public String getMasterProject() {
        return masterProject;
    }

    /**
     * Set master project name.
     *
     * @param masterProject
     *            Master project name.
     */
    public void setMasterProject(final String masterProject) {
        this.masterProject = masterProject;
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

    /**
     * Get the creation date of this entity.
     *
     * @return Creation date.
     */
    public Timestamp getInsertDate() {
        if (this.insertDate == null) {
            return null;
        }
        return Timestamp.valueOf(insertDate.toLocalDateTime());
    }

    /**
     * Set the creation date of this entity.
     *
     * @param insertDate
     *            Creation date.
     */
    public void setInsertDate(final Timestamp insertDate) {
        if (insertDate == null) {
            this.insertDate = null;
            return;
        }
        this.insertDate = Timestamp.valueOf(insertDate.toLocalDateTime());
    }

    /**
     * Is deployment in progress.
     *
     * @return The progress state.
     */
    public boolean isInProgress() {
        return inProgress;
    }

    /**
     * Set deployment's progress state.
     *
     * @param inProgress
     *            The progress state to set.
     */
    public void setInProgress(final boolean inProgress) {
        this.inProgress = inProgress;
    }
}
