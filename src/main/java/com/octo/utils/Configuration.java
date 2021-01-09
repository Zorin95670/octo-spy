package com.octo.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Classe that contains all configuration properties in application.properties.
 *
 * @author vmoittie
 *
 */
@Component
public class Configuration {

    /**
     * Project's name.
     */
    @Value("${project.name}")
    private String project;

    /**
     * Project's version.
     */
    @Value("${project.version}")
    private String version;

    /**
     * Project's environment.
     */
    @Value("${project.environment}")
    private String environment;

    /**
     * Project's client.
     */
    @Value("${project.client}")
    private String client;

    /**
     * Default value for API limit.
     */
    @Value("${api.default.limit:10}")
    private int defaultApiLimit;

    /**
     * Maximum value for API limit.
     */
    @Value("${api.maximum.limit:100}")
    private int maximumApiLimit;

    /**
     * Get project's name.
     *
     * @return Project name.
     */
    public String getProject() {
        return this.project;
    }

    /**
     * Get project's version.
     *
     * @return the project's version.
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * Get project's environment.
     *
     * @return Project's environments.
     */
    public String getEnvironment() {
        return this.environment;
    }

    /**
     * Get project's client.
     *
     * @return Project's client.
     */
    public String getClient() {
        return this.client;
    }

    /**
     * Get the default API limit.
     *
     * @return The default API limit.
     */
    public int getDefaultApiLimit() {
        return this.defaultApiLimit;
    }

    /**
     * Get the maximum API limit.
     *
     * @return the maximum API limit.
     */
    public int getMaximumApiLimit() {
        return this.maximumApiLimit;
    }

}
