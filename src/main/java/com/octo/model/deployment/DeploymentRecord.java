package com.octo.model.deployment;

import javax.validation.constraints.NotBlank;

/**
 * DTO to call on create deployment.
 *
 * @param environment Deployment's environment.
 * @param project     Deployment's project.
 * @param version     Deployment's version.
 * @param client      Client.
 * @param alive       Is deployment is still alive.
 * @param inProgress  Is deployment is in progress.
 * @author Vincent Moittié
 */
public record DeploymentRecord(
        @NotBlank String environment,
        @NotBlank String project,
        @NotBlank String version,
        @NotBlank String client,
        boolean alive,
        boolean inProgress) {
}
