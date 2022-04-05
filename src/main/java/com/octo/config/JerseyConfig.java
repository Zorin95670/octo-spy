package com.octo.config;

import com.octo.controller.AdministratorController;
import com.octo.controller.AlertsController;
import com.octo.controller.ClientController;
import com.octo.controller.DashboardController;
import com.octo.controller.DeploymentController;
import com.octo.controller.EnvironmentController;
import com.octo.controller.InfoController;
import com.octo.controller.ProjectController;
import com.octo.controller.ReportController;
import com.octo.controller.UserController;
import com.octo.controller.handler.ConstraintViolationExceptionHandler;
import com.octo.controller.handler.GlobalExceptionHandler;
import com.octo.security.AuthenticationFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

/**
 * Jersey configuration.
 */
@Configuration
public class JerseyConfig extends ResourceConfig {

    /**
     * Default constructor to initialize registered endpoints and filters.
     */
    public JerseyConfig() {
        // Filter
        register(AuthenticationFilter.class);
        // Controller
        register(AdministratorController.class);
        register(AlertsController.class);
        register(ClientController.class);
        register(DashboardController.class);
        register(DeploymentController.class);
        register(EnvironmentController.class);
        register(InfoController.class);
        register(ProjectController.class);
        register(ReportController.class);
        register(UserController.class);
        // Exception handler
        register(GlobalExceptionHandler.class);
        register(ConstraintViolationExceptionHandler.class);
    }
}
