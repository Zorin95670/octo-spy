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

/**
 * Controller to manage environment.
 *
 * @author Vincent Moittié
 *
 */
@Path("/environment")
@Produces(MediaType.APPLICATION_JSON)
@Controller
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
    @PermitAll
    public final List<EnvironmentDTO> getAll() {
        LOGGER.info("Receive GET request to get  all environments.");
        return service.findAll();
    }
}
