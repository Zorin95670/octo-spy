package com.octo.model.dto.deployment;

import com.octo.model.dto.common.AbstractDateDTO;
import com.octo.model.entity.Environment;
import com.octo.model.entity.Project;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Default deployment DTO.
 *
 * @author Vincent Moittié
 *
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public @Data class DeploymentDTO extends AbstractDateDTO {
    /**
     * Primary key.
     */
    private Long id;
    /**
     * Index of project.
     */
    private Long projectId;
    /**
     * Deployment's environment name.
     */
    private String environment;
    /**
     * Deployment's project name.
     */
    private String project;
    /**
     * Deployment's project color.
     */
    private String color;
    /**
     * Deployment's master project name.
     */
    private String masterProject;
    /**
     * Deployment's master project color.
     */
    private String masterProjectColor;
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
