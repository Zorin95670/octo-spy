package com.octo.controller;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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

import com.octo.model.authentication.UserRoleType;
import com.octo.model.common.Resource;
import com.octo.model.dto.count.CountDTO;
import com.octo.model.dto.deployment.DeploymentDTO;
import com.octo.model.dto.deployment.NewDeploymentRecord;
import com.octo.model.dto.deployment.SearchDeploymentViewDTO;
import com.octo.model.dto.deployment.SearchLastDeploymentViewDTO;
import com.octo.model.entity.DeploymentView;
import com.octo.service.CountService;
import com.octo.service.DeploymentService;
import com.octo.service.LastDeploymentViewService;
import com.octo.utils.bean.BeanMapper;

/**
 * Deployment controller.
 *
 * @author Vincent Moittié
 *
 */
@Path("/deployments")
@Produces(MediaType.APPLICATION_JSON)
@Controller
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
     * Service to manage count.
     */
    @Autowired
    private CountService countService;

    /**
     * Count field of deployments for restricted value.
     *
     * @param countBody
     *            CountDTO
     * @param deploymentDTO
     *            deployment's filter.
     * @return Resource to contains deployments and total of this.
     */
    @GET
    @PermitAll
    @Path("/count")
    public Response count(@BeanParam final CountDTO countBody,
            @BeanParam final SearchLastDeploymentViewDTO deploymentDTO) {
        LOGGER.info("Received GET request to count deployments with count DTO {} and search DTO {}.", countBody,
                deploymentDTO);
        CountDTO countDTO = new BeanMapper<>(CountDTO.class).apply(countBody);
        SearchLastDeploymentViewDTO dto = new BeanMapper<>(SearchLastDeploymentViewDTO.class).apply(deploymentDTO);
        return Response.ok(this.countService.count(DeploymentView.class, countDTO, dto)).build();
    }

    /**
     * Endpoint to return a last deployments.
     *
     * @param dto
     *            Deployment's filter.
     *
     * @return Deployments.
     */
    @GET
    @Path("/last")
    @PermitAll
    public final Response getLastDeployments(final @BeanParam SearchLastDeploymentViewDTO dto) {
        LOGGER.info("Receive GET request to get last deployment with {}.", dto);
        return Response.ok(this.lastDeploymentViewService.find(dto)).build();
    }

    /**
     * Endpoint to return a specific deployment.
     *
     * @param id
     *            Deployment's id.
     * @return Deployment.
     */
    @GET
    @Path("/{id}")
    @PermitAll
    public final Response getDeployment(@PathParam("id") final Long id) {
        LOGGER.info("Receive GET request to get deployment with id {}.", id);
        return Response.ok(this.service.load(id)).build();
    }

    /**
     * Endpoint to return a deployments list.
     *
     * @param dto
     *            Filter.
     * @return Deployments.
     */
    @GET
    @PermitAll
    public final Response getDeployments(final @BeanParam SearchDeploymentViewDTO dto) {
        LOGGER.info("Receive GET request to get deployments with {}.", dto);
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
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRoleType.ADMIN, UserRoleType.PROJECT_MANAGER, UserRoleType.TOKEN})
    public final Response createDeployment(final NewDeploymentRecord dto) {
        LOGGER.info("Receive POST request to create deployment with dto {}.", dto);
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @RolesAllowed({UserRoleType.ADMIN, UserRoleType.PROJECT_MANAGER})
    public Response deleteDeployment(@PathParam("id") final Long id) {
        LOGGER.info("Receive DELETE request to delete deployment with id {}.", id);
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/progress")
    @RolesAllowed({UserRoleType.ADMIN, UserRoleType.PROJECT_MANAGER, UserRoleType.TOKEN})
    public final Response deleteProgressDeployment(final SearchDeploymentViewDTO dto) {
        LOGGER.info("Receive DELETE request to delete progress of deployment.");
        service.deleteProgressDeployment(dto);
        return Response.noContent().build();
    }
}
