package com.octo.model.dto.deployment;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.octo.model.common.DefaultDTO;
import com.octo.model.entity.Environment;
import com.octo.model.entity.Project;

/**
 * Default deployment DTO.
 */
public class DeploymentDTO extends DefaultDTO {
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
     * Is deployment is sill alive.
     */
    private boolean alive;
    /**
     * The creation date of this row.
     */
    @JsonFormat(pattern = "YYYY/MM/DD HH:mm:ss")
    private Timestamp insertDate;
    /**
     * The last update date ot this row.
     */
    @JsonFormat(pattern = "YYYY/MM/DD HH:mm:ss")
    private Timestamp updateDate;

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
     * Set environment's name.
     *
     * @param entity
     *            Environment entity.
     */
    public void setEnvironment(final Environment entity) {
        String name = null;

        if (entity != null) {
            name = entity.getName();
        }

        this.setEnvironment(name);
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
     * Set project's name.
     *
     * @param entity
     *            Project entity.
     */
    public void setProject(final Project entity) {
        String name = null;

        if (entity != null) {
            name = entity.getName();
        }

        this.setProject(name);
    }

    /**
     * Get master project's name.
     *
     * @return Master project's name.
     */
    public String getMasterProject() {
        return masterProject;
    }

    /**
     * Set master project's name.
     *
     * @param masterProject
     *            Master project's name.
     */
    public void setMasterProject(final String masterProject) {
        this.masterProject = masterProject;
    }

    /**
     * Set master project's name.
     *
     * @param entity
     *            Master project entity.
     */
    public void setMasterProject(final Project entity) {
        String name = null;

        if (entity != null) {
            name = entity.getName();
        }

        this.setMasterProject(name);
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
     * Get the last update date of this entity.
     *
     * @return Last update date.
     */
    public Timestamp getUpdateDate() {
        if (this.updateDate == null) {
            return null;
        }
        return Timestamp.from(updateDate.toInstant());
    }

    /**
     * Set the last update date of this entity.
     *
     * @param updateDate
     *            Last update date.
     */
    public void setUpdateDate(final Timestamp updateDate) {
        if (updateDate == null) {
            this.updateDate = null;
            return;
        }
        this.updateDate = Timestamp.from(updateDate.toInstant());
    }
}
