package com.octo.model.dto.common;

import com.octo.model.common.DefaultDTO;

/**
 * All project's information.
 */
public class ProjectInformation extends DefaultDTO {

    /**
     * Project's name.
     */
    private final String project;
    /**
     * Project's version.
     */
    private final String version;
    /**
     * Project's environment.
     */
    private final String environment;
    /**
     * Project's client.
     */
    private final String client;

    /**
     * Default constructor.
     *
     * @param project
     *            Project's name.
     * @param version
     *            Project's version.
     * @param environment
     *            Project's environment.
     * @param client
     *            Project's client.
     */
    public ProjectInformation(final String project, final String version, final String environment,
            final String client) {
        this.project = project;
        this.version = version;
        this.environment = environment;
        this.client = client;
    }

    /**
     * Get project's name.
     *
     * @return Project's name.
     */
    public final String getProject() {
        return this.project;
    }

    /**
     * Get project's version.
     *
     * @return Project's version.
     */
    public final String getVersion() {
        return this.version;
    }

    /**
     * Get project's environment.
     *
     * @return Project's environment.
     */
    public final String getEnvironment() {
        return this.environment;
    }

    /**
     * Get project's client.
     *
     * @return Project's client.
     */
    public final String getClient() {
        return this.client;
    }
}
