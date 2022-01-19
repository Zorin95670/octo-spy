package com.octo.controller;

import com.octo.controller.model.QueryFilter;
import com.octo.model.common.UserRoleType;
import com.octo.model.project.ProjectRecord;
import com.octo.persistence.model.Project;
import com.octo.persistence.model.ProjectView;
import com.octo.service.ProjectService;
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
 * Project controller.
 *
 * @author Vincent Moittié
 */
@Path("/projects")
@Produces(MediaType.APPLICATION_JSON)
@Controller
public class ProjectController {

    /**
     * Logger.
     **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

    /**
     * Project service.
     */
    @Autowired
    private ProjectService service;

    /**
     * Count field of project for restricted value.
     *
     * @param uriInfo Uri info to get query parameters.
     * @param field   Field to count.
     * @param value   Default value for count.
     * @return Resource to contains projects and total of this.
     */
    @GET
    @PermitAll
    @Path("/count")
    public Response count(final @Context UriInfo uriInfo, @Valid @NotBlank @QueryParam("field") final String field,
                          @QueryParam("value") final String value) {
        Map<String, String> filters = ControllerHelper.getFilters(uriInfo);
        LOGGER.info("Received GET request to count projects with filters {}.", filters);
        return Response.ok(this.service.count(filters, field, value)).build();
    }

    /**
     * Get specific project.
     *
     * @param id Deployment's id.
     * @return Deployment.
     */
    @GET
    @Path("/{id}")
    @PermitAll
    public final Response getProject(@PathParam("id") @Valid @NotNull final Long id) {
        LOGGER.info("Receive GET request to get project with id {}.", id);
        return Response.ok(this.service.load(id)).build();
    }

    /**
     * Update a specific project.
     *
     * @param id      Project's id.
     * @param project Project's record.
     * @return Deployment.
     */
    @PATCH
    @Path("/{id}")
    @RolesAllowed({UserRoleType.ADMIN})
    public final Response updateProject(@PathParam("id") @Valid @NotNull final Long id,
                                        final Project project)
            throws InvocationTargetException, IllegalAccessException {
        LOGGER.info("Receive PATCH request to update project with id {} and {}.", id, project);
        this.service.update(id, project);
        return Response.noContent().build();
    }

    /**
     * Endpoint to return all projects.
     *
     * @param uriInfo     Uri info to get query parameters.
     * @param queryFilter Query filter for sort and pagination.
     * @return List of project name.
     */
    @GET
    @PermitAll
    public final Response getProjects(final @Context UriInfo uriInfo, final @BeanParam @Valid QueryFilter queryFilter) {
        Map<String, String> filters = ControllerHelper.getFilters(uriInfo);
        LOGGER.info("Receive GET request to get all projects with {}.", filters);
        Page<ProjectView> projects = this.service.findAll(filters, queryFilter.getPagination());
        return Response.status(ControllerHelper.getStatus(projects)).entity(projects).build();
    }

    /**
     * Create project.
     *
     * @param dto Project DTO.
     * @return Project.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRoleType.ADMIN})
    public final Response createProject(@Valid final ProjectRecord dto) {
        LOGGER.info("Receive POST request to create project with dto {}.", dto);
        return Response.ok(this.service.save(dto)).status(Status.CREATED).build();
    }

    /**
     * Delete project in database.
     *
     * @param id Id of project to search.
     * @return No content.
     */
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @RolesAllowed({UserRoleType.ADMIN})
    public Response deleteProject(@PathParam("id") @Valid @NotNull final Long id) {
        LOGGER.info("Receive DELETE request to delete project with id {}.", id);
        this.service.delete(id);
        return Response.noContent().build();
    }
}
