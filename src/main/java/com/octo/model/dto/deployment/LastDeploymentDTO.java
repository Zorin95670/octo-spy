package com.octo.model.dto.deployment;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.octo.model.common.DefaultDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Last alive deployment DTO.
 *
 * @author Vincent Moittié
 *
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public @Data class LastDeploymentDTO extends DefaultDTO {
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
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp insertDate;
}
