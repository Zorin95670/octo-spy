package com.octo.controller;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import com.octo.model.common.Resource;
import com.octo.model.dto.deployment.DeploymentDTO;
import com.octo.model.dto.deployment.NewDeploymentDTO;
import com.octo.model.dto.deployment.SearchDeploymentDTO;
import com.octo.model.dto.deployment.SearchDeploymentViewDTO;
import com.octo.service.DeploymentService;
import com.octo.service.LastDeploymentViewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
     * Deployment service.
     */
    @Autowired
    private LastDeploymentViewService lastDeploymentViewService;

    /**
     * Endpoint to return a last deployments.
     *
     * @return Deployments.
     */
    @GET
    @Path("/last")
    public final Response getLastDeployments() {
        LOGGER.info("Receive GET request to get last deployment");
        return Response.ok(this.lastDeploymentViewService.find()).build();
    }

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
    public final Response getDeployment(@PathParam("id") final Long id) {
        LOGGER.info("Receive GET request to get deployment with id {}", id);
        return Response.ok(this.service.load(id)).build();
    }

    /**
     * Endpoint to return a deployments list.
     *
     * @param dto
     *            Filter.
     * @return Deployments.
     * @throws OctoException
     *             On all database error.
     */
    @GET
    public final Response getDeployments(final @BeanParam SearchDeploymentViewDTO dto) {
        LOGGER.info("Receive GET request to get deployments with {}", dto);
        final Resource<DeploymentDTO> resources = this.service.find(dto);
        int status = HttpStatus.OK.value();
        if (!Long.valueOf(resources.getResources().size()).equals(resources.getTotal())) {
            status = HttpStatus.PARTIAL_CONTENT.value();
        }
        return Response.status(status).entity(resources).build();
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
    public final Response createDeployment(final NewDeploymentDTO dto) {
        LOGGER.info("Receive POST request to create deployment with dto {}", dto);
        return Response.ok(this.service.save(dto)).status(Status.CREATED).build();
    }

    /**
     * Delete deployment in database.
     *
     * @param id
     *            Id of deployment to search.
     * @return No content.
     */
    @DELETE
    @Operation(summary = "Delete deployment in database.",
            responses = { @ApiResponse(responseCode = "204"), @ApiResponse(responseCode = "404",
                    content = @Content(mediaType = "application/json", schema = @Schema(allOf = { Error.class })),
                    description = "Error on unknown deployment id.") })
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deleteDeployment(@PathParam("id") final Long id) {
        LOGGER.info("Receive DELETE request to delete deployment with id {}", id);
        this.service.delete(id);
        return Response.noContent().build();
    }

    /**
     * Delete progress of deployment.
     *
     * @param dto
     *            Filter to search progress.
     * @return No content or error.
     */
    @DELETE
    @Operation(summary = "Delete progress of deployment.",
            responses = { @ApiResponse(responseCode = "204"), @ApiResponse(responseCode = "404",
                    content = @Content(mediaType = "application/json", schema = @Schema(allOf = { Error.class })),
                    description = "Error on no progress to delete.") })
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/progress")
    public final Response deleteProgressDeployment(final SearchDeploymentDTO dto) {
        LOGGER.info("Receive DELETE request to delete progress of deployment");
        service.deleteProgressDeployment(dto);
        return Response.noContent().build();
    }
}
