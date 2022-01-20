package com.octo.controller;

import com.octo.controller.model.QueryFilter;
import com.octo.model.common.UserRoleType;
import com.octo.model.deployment.DeploymentDTO;
import com.octo.model.deployment.DeploymentRecord;
import com.octo.model.deployment.DeploymentViewDTO;
import com.octo.persistence.model.Deployment;
import com.octo.persistence.model.DeploymentView;
import com.octo.persistence.model.LastDeploymentView;
import com.octo.service.DeploymentService;
import com.octo.service.LastDeploymentViewService;
import com.octo.utils.bean.BeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Deployment controller.
 *
 * @author Vincent Moittié
 */
@Path("/deployments")
@Produces(MediaType.APPLICATION_JSON)
@Controller
public class DeploymentController {

    /**
     * Logger.
     **/
    private static final Logger LOGGER = LoggerFactory.getLogger(DeploymentController.class);

    /**
     * Deployment service.
     */
    @Autowired
    private DeploymentService deploymentService;

    /**
     * Last deployment view service.
     */
    @Autowired
    private LastDeploymentViewService lastDeploymentViewService;

    /**
     * Count field of deployments for restricted value.
     *
     * @param uriInfo Request URI information.
     * @param field   Field to count
     * @param value Default value to count
     * @return Count's object.
     */
    @GET
    @PermitAll
    @Path("/count")
    public Response count(final @Context UriInfo uriInfo, final @Valid @NotBlank @QueryParam("field") String field,
                          final @QueryParam("value") String value) {
        Map<String, String> filters = ControllerHelper.getFilters(uriInfo);
        LOGGER.info("Received GET request to count deployments with filters {}.", filters);
        return Response.ok(this.deploymentService.count(filters, field, value)).build();
    }

    /**
     * Get all last deployments.
     *
     * @param uriInfo     Request URI information.
     * @param queryFilter Sort and pagination options.
     * @return Last deployments paginated.
     */
    @GET
    @Path("/last")
    @PermitAll
    public final Response getLastDeployments(final @Context UriInfo uriInfo,
                                             final @BeanParam @Valid QueryFilter queryFilter) {
        Map<String, String> filters = ControllerHelper.getFilters(uriInfo);
        LOGGER.info("Receive GET request to get last deployment with {} and {}.", filters, queryFilter);
        final Page<LastDeploymentView> resources =
                this.lastDeploymentViewService.find(filters, queryFilter.getPagination());
        return Response.status(ControllerHelper.getStatus(resources)).entity(resources).build();
    }

    /**
     * Get deployment by id.
     *
     * @param id Deployment's id.
     * @return Deployment.
     */
    @GET
    @Path("/{id}")
    @PermitAll
    public final Response getDeployment(@PathParam("id") @Valid @NotNull final Long id) {
        LOGGER.info("Receive GET request to get deployment with id {}.", id);
        return Response.ok(new BeanMapper<>(DeploymentViewDTO.class)
                .apply(this.deploymentService.loadView(id))).build();
    }

    /**
     * Get deployments.
     *
     * @param uriInfo     Request URI information.
     * @param queryFilter Sort and pagination option.
     * @return Deployments paginated.
     */
    @GET
    @PermitAll
    public final Response getDeployments(final @Context UriInfo uriInfo,
                                         final @BeanParam @Valid QueryFilter queryFilter) {
        Map<String, String> filters = ControllerHelper.getFilters(uriInfo);
        LOGGER.info("Receive GET request to get deployments with {}.", filters);
        final Page<DeploymentDTO> resources = this.deploymentService.find(filters, queryFilter.getPagination())
                .map(new BeanMapper<>(DeploymentDTO.class));
        return Response.status(ControllerHelper.getStatus(resources)).entity(resources).build();
    }

    /**
     * Create deployment.
     *
     * @param newDeployment Deployment record.
     * @return Deployment.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRoleType.ADMIN, UserRoleType.TOKEN})
    public final Response createDeployment(final @Valid DeploymentRecord newDeployment) {
        LOGGER.info("Receive POST request to create deployment with {}.", newDeployment);
        DeploymentView deployment = this.deploymentService.save(newDeployment);
        return Response.ok(new BeanMapper<>(DeploymentViewDTO.class)
                        .apply(this.deploymentService.loadView(deployment.getId())))
                        .status(Status.CREATED).build();
    }

    /**
     * Update a specific deployment.
     *
     * @param id         Deployment's id.
     * @param deployment Deployment's record.
     * @return No content response.
     * @throws InvocationTargetException If the property accessor method throws an exception.
     * @throws IllegalAccessException    If the caller does not have access to the property accessor
     *                                   method.
     */
    @PATCH
    @Path("/{id}")
    @RolesAllowed({UserRoleType.ADMIN, UserRoleType.TOKEN})
    public final Response updateProject(@PathParam("id") @Valid @NotNull final Long id,
                                        final Deployment deployment)
            throws IllegalAccessException, InvocationTargetException {
        LOGGER.info("Receive PATCH request to update project with id {} and {}.", id, deployment);
        this.deploymentService.update(id, deployment);
        return Response.noContent().build();
    }

    /**
     * Delete deployment in database.
     *
     * @param id Id of deployment to search.
     * @return No content.
     */
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @RolesAllowed({UserRoleType.ADMIN})
    public Response deleteDeployment(@PathParam("id") @Valid @NotNull final Long id) {
        LOGGER.info("Receive DELETE request to delete deployment with id {}.", id);
        this.deploymentService.delete(id);
        return Response.noContent().build();
    }

    /**
     * Delete progress of deployment.
     *
     * @param uriInfo Uri info to get query parameters.
     * @return No content or error.
     */
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/progress")
    @RolesAllowed({UserRoleType.ADMIN, UserRoleType.TOKEN})
    public final Response deleteProgressDeployment(final @Context UriInfo uriInfo) {
        Map<String, String> filters = ControllerHelper.getFilters(uriInfo);
        LOGGER.info("Receive DELETE request to delete progress of deployment with {}.", filters);
        deploymentService.deleteProgressDeployment(filters);
        return Response.noContent().build();
    }
}
