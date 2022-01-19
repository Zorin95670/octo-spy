package com.octo.controller;

import com.octo.controller.model.QueryFilter;
import com.octo.model.common.UserRoleType;
import com.octo.model.environment.EnvironmentRecord;
import com.octo.persistence.model.Environment;
import com.octo.service.EnvironmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import java.util.Map;

/**
 * Controller to manage environment.
 *
 * @author Vincent Moittié
 */
@Path("/environments")
@Produces(MediaType.APPLICATION_JSON)
@Controller
public class EnvironmentController {

    /**
     * Logger.
     **/
    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentController.class);

    /**
     * Environment service.
     */
    @Autowired
    private EnvironmentService service;

    /**
     * Endpoint to return all environments.
     *
     * @param uriInfo Uri info to get query parameters.
     * @param queryFilter Query filter for sort and pagination.
     * @return Environments.
     */
    @GET
    @PermitAll
    public final Response getAll(final @Context UriInfo uriInfo, final @BeanParam @Valid QueryFilter queryFilter) {
        Map<String, String> filters = ControllerHelper.getFilters(uriInfo);
        LOGGER.info("Receive GET request to get  all environments with {}", filters);
        Page<Environment> resources =
                service.findAll(filters, queryFilter.getPagination());

        return Response.status(ControllerHelper.getStatus(resources)).entity(resources).build();
    }

    /**
     * Create environment.
     *
     * @param environment Environment DTO.
     * @return Environment.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRoleType.ADMIN})
    public final Response createEnvironment(@Valid final EnvironmentRecord environment) {
        LOGGER.info("Receive POST request to create environment with {}.", environment);
        return Response.ok(this.service.save(environment)).status(Status.CREATED).build();
    }

    /**
     * Update a specific environment.
     *
     * @param id          Environment's id.
     * @param environment Environment's record.
     * @return No content.
     */
    @PATCH
    @Path("/{id}")
    @RolesAllowed({UserRoleType.ADMIN})
    public final Response updateEnvironment(@PathParam("id") @Valid @NotNull final Long id,
                                            @Valid final EnvironmentRecord environment) {
        LOGGER.info("Receive PATCH request to update environment with id {} and {}.", id, environment);
        this.service.update(id, environment);
        return Response.noContent().build();
    }

    /**
     * Delete environment in database.
     *
     * @param id Id of environment to search.
     * @return No content.
     */
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @RolesAllowed({UserRoleType.ADMIN})
    public Response deleteEnvironment(@PathParam("id") @Valid @NotNull final Long id) {
        LOGGER.info("Receive DELETE request to delete environment with id {}.", id);
        this.service.delete(id);
        return Response.noContent().build();
    }
}
