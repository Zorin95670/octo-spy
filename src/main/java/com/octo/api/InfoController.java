package com.octo.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.octo.models.ProjectInformation;
import com.octo.utils.Configuration;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * Controller to manage project version.
 *
 * @author vmoittie
 *
 */
@Path("/info")
@Produces(MediaType.APPLICATION_JSON)
@Controller
@Server(url = "/octo-spy/api")
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
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProjectInformation.class)),
            description = "Project's information.")
    public final ProjectInformation getVersion() {
        return new ProjectInformation(this.configuration.getProject(), this.configuration.getVersion(),
                this.configuration.getEnvironment(), this.configuration.getClient());
    }
}
