package com.octo.controller;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.octo.dao.IDAO;
import com.octo.model.entity.Environment;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * Controller to manage environment.
 *
 * @author vmoittie
 *
 */
@Path("/environment")
@Produces(MediaType.APPLICATION_JSON)
@Controller
@Server(url = "/octo-spy/api")
public class EnvironmentController {

    /**
     * Environment dao.
     */
    @Autowired
    private IDAO<Environment> environmentDAO;

    /**
     * Endpoint to return all environments.
     *
     * @return Environments.
     */
    @GET
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Environment.class)),
            description = "Environment's list.")
    public final List<Environment> getAll() {
        return environmentDAO.findAll();
    }
}
