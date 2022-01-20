package com.octo.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class that contains all configuration properties in application.properties.
 *
 * @author Vincent Moittié
 */
@Component
public class Configuration {

    /**
     * Project's name.
     */
    @Getter
    @Value("${application.name}")
    private String project;

    /**
     * Project's version.
     */
    @Getter
    @Value("${application.version}")
    private String version;

    /**
     * Project's environment.
     */
    @Getter
    @Value("${application.environment}")
    private String environment;

    /**
     * Project's client.
     */
    @Getter
    @Value("${application.client}")
    private String client;
}
