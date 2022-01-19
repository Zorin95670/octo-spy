package com.octo.controller;

import com.octo.config.Configuration;
import com.octo.model.common.ProjectInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Controller to manage project version.
 *
 * @author Vincent Moittié
 */
@Path("/info")
@Produces(MediaType.APPLICATION_JSON)
@Controller
public class InfoController {

    /**
     * Configuration class.
     */
    @Autowired
    private Configuration configuration;

    /**
     * Endpoint to return version of project.
     *
     * @return Project version.
     */
    @GET
    @PermitAll
    public final Response getVersion() {
        return Response.ok(new ProjectInformation(this.configuration.getProject(), this.configuration.getVersion(),
                this.configuration.getEnvironment(), this.configuration.getClient())).build();
    }
}
