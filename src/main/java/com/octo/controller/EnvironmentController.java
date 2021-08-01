package com.octo.controller;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.octo.model.dto.environment.EnvironmentDTO;
import com.octo.service.EnvironmentService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * Controller to manage environment.
 *
 * @author Vincent Moittié
 *
 */
@Path("/environment")
@Produces(MediaType.APPLICATION_JSON)
@Controller
@Server(url = "/octo-spy/api")
public class EnvironmentController {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentController.class);

    /**
     * Environment service.
     */
    @Autowired
    private EnvironmentService service;

    /**
     * Endpoint to return all environments.
     *
     * @return Environments.
     */
    @GET
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnvironmentDTO.class)),
            description = "Environment's list.")
    @PermitAll
    public final List<EnvironmentDTO> getAll() {
        LOGGER.info("Receive GET request to get  all environment");
        return service.findAll();
    }
}
