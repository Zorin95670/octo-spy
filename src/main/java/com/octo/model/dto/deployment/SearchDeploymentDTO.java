package com.octo.model.dto.deployment;

import com.octo.model.entity.Environment;
import com.octo.model.entity.Project;
import com.octo.utils.predicate.filter.FilterType;
import com.octo.utils.predicate.filter.FilterType.Type;
import com.octo.utils.predicate.filter.QueryFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO to search deployment.
 *
 * @author Vincent Moittié
 *
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public @Data class SearchDeploymentDTO extends QueryFilter {
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
        String name = null;

        if (entity != null) {
            name = entity.getName();
        }

        this.setProject(name);
    }

}
