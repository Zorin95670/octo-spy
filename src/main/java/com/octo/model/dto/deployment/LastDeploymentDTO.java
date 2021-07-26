package com.octo.model.dto.deployment;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.octo.model.common.DefaultDTO;

/**
 * Last alive deployment DTO.
 *
 * @author Vincent Moittié
 *
 */
public class LastDeploymentDTO extends DefaultDTO {
    /**
     * Primary key.
     */
    private Long id;
    /**
     * Deployment's environment name.
     */
    private String environment;
    /**
     * Deployment's project name.
     */
    private String project;
    /**
     * Deployment's master project name.
     */
    private String masterProject;
    /**
     * Deployed version.
     */
    private String version;
    /**
     * Client.
     */
    private String client;
    /**
     * Is deployment in progress.
     */
    private boolean inProgress;

    /**
     * Is deployment on master project.
     */
    private boolean onMasterProject;
    /**
     * The creation date of this row.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp insertDate;

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
     * Get environment's name.
     *
     * @return Environment's name.
     */
    public String getEnvironment() {
        return environment;
    }

    /**
     * Set environment's name.
     *
     * @param environment
     *            Environment's name.
     */
    public void setEnvironment(final String environment) {
        this.environment = environment;
    }

    /**
     * Get project's name.
     *
     * @return Project's name.
     */
    public String getProject() {
        return project;
    }

    /**
     * Set project's name.
     *
     * @param project
     *            Project's name.
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
     * Get version.
     *
     * @return Version.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Set version.
     *
     * @param version
     *            Version.
     */
    public void setVersion(final String version) {
        this.version = version;
    }

    /**
     * Get client.
     *
     * @return Client.
     */
    public String getClient() {
        return client;
    }

    /**
     * Set client.
     *
     * @param client
     *            Client.
     */
    public void setClient(final String client) {
        this.client = client;
    }

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
     * @param onMasterProject
     *            On master project.
     */
    public void setAlive(final boolean onMasterProject) {
        this.onMasterProject = onMasterProject;
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
        return Timestamp.from(insertDate.toInstant());
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
        this.insertDate = Timestamp.from(insertDate.toInstant());
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
