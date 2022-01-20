package com.octo.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Model for info endpoint.
 */
@AllArgsConstructor
@Data
public class ProjectInformation {
    /**
     * Project name.
     */
    private String project;
    /**
     * Deployed version.
     */
    private String version;
    /**
     * Current environment name.
     */
    private String environment;
    /**
     * Current client name.
     */
    private String client;
}
