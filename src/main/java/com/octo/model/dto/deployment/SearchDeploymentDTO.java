package com.octo.model.dto.deployment;

import com.octo.model.entity.Environment;
import com.octo.model.entity.Project;
import com.octo.utils.predicate.filter.FilterType;
import com.octo.utils.predicate.filter.FilterType.Type;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * DTO to search deployment.
 */
public class SearchDeploymentDTO extends QueryFilter {
    /**
     * Primary key.
     */
    @FilterType(type = Type.NUMBER)
    private String id;
    /**
     * Deployment's environment name.
     */
    @FilterType(type = Type.NUMBER)
    private String environment;
    /**
     * Deployment's project name.
     */
    @FilterType(type = Type.NUMBER)
    private String project;
    /**
     * Deployed version.
     */
    @FilterType(type = Type.TEXT)
    private String version;
    /**
     * Client.
     */
    @FilterType(type = Type.TEXT)
    private String client;
    /**
     * Is deployment is sill alive.
     */
    @FilterType(type = Type.BOOLEAN)
    private String alive;

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
     * Set deployement alive state.
     *
     * @param alive
     *            Alive state.
     */
    public void setAlive(final String alive) {
        this.alive = alive;
    }

}
