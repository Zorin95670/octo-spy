package com.octo.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
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

import com.octo.model.authentication.UserRoleType;
import com.octo.model.dto.environment.EnvironmentDTO;
import com.octo.model.dto.environment.NewEnvironmentRecord;
import com.octo.service.EnvironmentService;

/**
 * Controller to manage environment.
 *
 * @author Vincent Moittié
 *
 */
@Path("/environments")
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

    /**
     * Create environment.
     *
     * @param dto
     *            Environment DTO.
     * @return Environment.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRoleType.ADMIN, UserRoleType.PROJECT_MANAGER})
    public final Response createEnvironment(final NewEnvironmentRecord dto) {
        LOGGER.info("Receive POST request to create environment with dto {}.", dto);
        return Response.ok(this.service.save(dto)).status(Status.CREATED).build();
    }

    /**
     * Update a specific environment.
     *
     * @param id
     *            Environment's id.
     * @param environment
     *            Environment's record.
     * @return No content.
     * @throws InvocationTargetException
     *             If the property accessor method throws an exception.
     * @throws IllegalAccessException
     *             If the caller does not have access to the property accessor
     *             method.
     */
    @PATCH
    @Path("/{id}")
    @RolesAllowed({UserRoleType.ADMIN, UserRoleType.PROJECT_MANAGER})
    public final Response updateEnvironment(@PathParam("id") final Long id, final NewEnvironmentRecord environment)
            throws IllegalAccessException, InvocationTargetException {
        LOGGER.info("Receive PATCH request to update environment with id {} and {}.", id, environment);
        this.service.update(id, environment);
        return Response.noContent().build();
    }

    /**
     * Delete environment in database.
     *
     * @param id
     *            Id of environment to search.
     * @return No content.
     */
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @RolesAllowed({UserRoleType.ADMIN, UserRoleType.PROJECT_MANAGER})
    public Response deleteEnvironment(@PathParam("id") final Long id) {
        LOGGER.info("Receive DELETE request to delete environment with id {}.", id);
        this.service.delete(id);
        return Response.noContent().build();
    }
}
