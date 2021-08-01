package com.octo.model.dto.common;

/**
 * All project's information.
 *
 * @param project
 *            Project's name.
 * @param version
 *            Project's version.
 * @param environment
 *            Project's environment.
 * @param client
 *            Project's client.
 *
 * @author Vincent Moittié
 *
 */
public record ProjectInformationRecord(String project, String version, String environment, String client) {
}
