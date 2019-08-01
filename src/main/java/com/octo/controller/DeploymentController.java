package com.octo.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.octo.model.dto.deployment.NewDeploymentDTO;
import com.octo.model.exception.OctoException;
import com.octo.service.DeploymentService;

import io.swagger.v3.oas.annotations.servers.Server;

/**
 * Deployment controller.
 *
 * @author vmoittie
 *
 */
@Path("/deployment")
@Produces(MediaType.APPLICATION_JSON)
@Controller
@Server(url = "/octo-spy/api")
public class DeploymentController {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(DeploymentController.class);

    /**
     * Deployment service.
     */
    @Autowired
    private DeploymentService service;

    /**
     * Endpoint to return a specific deployment.
     *
     * @param id
     *            Deployment's id.
     * @return Deployment.
     * @throws OctoException
     *             On all database error.
     */
    @GET
    @Path("/{id}")
    public final Response getDeployment(@PathParam("id") final Long id) throws OctoException {
        LOGGER.info("Receive GET request to get deployment with id {}", id);
        return Response.ok(this.service.loadById(id)).build();
    }

    /**
     * Create deployment.
     *
     * @param dto
     *            Deployment DTO.
     * @return Deployment.
     * @throws OctoException
     *             On all database error.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public final Response createDeployment(final NewDeploymentDTO dto) throws OctoException {
        LOGGER.info("Receive POST request to create deployment with dto {}", dto);
        return Response.ok(this.service.save(dto)).status(Status.CREATED).build();
    }
}
