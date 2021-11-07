package com.octo.model.dto.deployment;

import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.octo.model.entity.Environment;
import com.octo.model.entity.Project;
import com.octo.utils.predicate.filter.FilterType;
import com.octo.utils.predicate.filter.FilterType.Type;
import com.octo.utils.predicate.filter.QueryFilter;
import lombok.*;

/**
 * DTO to search deployment.
 *
 * @author Vincent Moittié
 *
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public @Data class SearchDeploymentViewDTO extends QueryFilter {
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
     * Set environment's name.
     *
     * @param entity
     *            Environment entity.
     */
    public void setEnvironmentFromEntity(final Environment entity) {
        String name = null;

        if (entity != null) {
            name = entity.getName();
        }

        this.setEnvironment(name);
    }

    /**
     * Set project's name.
     *
     * @param entity
     *            Project entity.
     */
    public void setProjectFromEntity(final Project entity) {
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
     * Set master project's name.
     *
     * @param entity
     *            Master project entity.
     */
    public void setMasterProjectFromEntity(final Project entity) {
        String name = null;

        if (entity != null) {
            name = entity.getName();
        }

        this.setMasterProject(name);
    }
}
