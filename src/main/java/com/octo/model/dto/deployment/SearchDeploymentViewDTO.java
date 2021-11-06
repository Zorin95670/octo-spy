package com.octo.model.dto.deployment;

import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.octo.model.entity.Environment;
import com.octo.model.entity.Project;
import com.octo.utils.predicate.filter.FilterType;
import com.octo.utils.predicate.filter.FilterType.Type;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * DTO to search deployment.
 *
 * @author Vincent Moittié
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchDeploymentViewDTO extends QueryFilter {
    /**
     * Primary key.
     */
    @FilterType(type = Type.NUMBER)
    @QueryParam("id")
    private String id;
    /**
     * Index of project.
     */
    @FilterType(type = Type.NUMBER)
    @QueryParam("projectId")
    private String projectId;
    /**
     * Deployment's environment name.
     */
    @FilterType(type = Type.TEXT)
    @QueryParam("environment")
    private String environment;
    /**
     * Deployment's project name.
     */
    @FilterType(type = Type.TEXT)
    @QueryParam("project")
    private String project;
    /**
     * Deployment's master project name.
     */
    @FilterType(type = Type.TEXT)
    @QueryParam("masterProject")
    private String masterProject;
    /**
     * Deployed version.
     */
    @FilterType(type = Type.TEXT)
    @QueryParam("version")
    private String version;
    /**
     * Client.
     */
    @FilterType(type = Type.TEXT)
    @QueryParam("client")
    private String client;
    /**
     * Is deployment is still alive.
     */
    @FilterType(type = Type.BOOLEAN)
    @QueryParam("alive")
    private String alive;
    /**
     * Is deployment is in progress.
     */
    @FilterType(type = Type.BOOLEAN)
    @QueryParam("inProgress")
    private String inProgress;

    /**
     * Get id.
     *
     * @return Id.
     */
    public String getId() {
        return id;
    }

    /**
     * Set id.
     *
     * @param id
     *            Id.
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Get project id.
     *
     * @return Id.
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * Set project id.
     *
     * @param projectId
     *            Id.
     */
    public void setProjectId(final String projectId) {
        this.projectId = projectId;
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
        String projectName = null;
        String projectId = null;
        if (entity != null) {
            projectName = entity.getName();
            projectId = Long.toString(entity.getId());
        }

        this.setProject(projectName);
        this.setProjectId(projectId);
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
    public String getAlive() {
        return alive;
    }

    /**
     * Set deployment alive state.
     *
     * @param alive
     *            Alive state.
     */
    public void setAlive(final String alive) {
        this.alive = alive;
    }

    /**
     * Set deployment in progress state.
     *
     * @param inProgress
     *            In progress state.
     */
    public void setInProgress(final String inProgress) {
        this.inProgress = inProgress;
    }

    /**
     * Is deployment in progress.
     *
     * @return In progress state.
     */
    public String getInProgress() {
        return inProgress;
    }
}
