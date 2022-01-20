package com.octo.model.deployment;

import com.octo.model.common.AbstractDateDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Default deployment DTO.
 *
 * @author Vincent Moittié
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DeploymentDTO extends AbstractDateDTO {
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
     * Is deployment is still alive.
     */
    private boolean alive;
}
