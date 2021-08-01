package com.octo.model.dto.deployment;

/**
 * DTO to call on create deployment.
 *
 * @param environment
 *            Deployment's environment.
 * @param project
 *            Deployment's project.
 * @param version
 *            Deployment's version.
 * @param client
 *            Client.
 * @param alive
 *            Is deployment is still alive.
 * @param inProgress
 *            Is deployment is in progress.
 *
 * @author Vincent Moittié
 *
 */
public record NewDeploymentRecord(String environment, String project, String version, String client, boolean alive,
        boolean inProgress) {
}
