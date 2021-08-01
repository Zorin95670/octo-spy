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
import org.springframework.stereotype.Controller;

import com.octo.model.authentication.UserRoleType;
import com.octo.model.dto.count.CountDTO;
import com.octo.model.dto.project.NewProjectRecord;
import com.octo.model.dto.project.SearchProjectViewDTO;
import com.octo.model.entity.ProjectView;
import com.octo.service.CountService;
import com.octo.service.ProjectService;
import com.octo.utils.bean.BeanMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * Project controller.
 *
 * @author Vincent Moittié
 *
 */
@Path("/project")
@Produces(MediaType.APPLICATION_JSON)
@Controller
@Server(url = "/octo-spy/api")
public class ProjectController {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

    /**
     * Project service.
     */
    @Autowired
    private ProjectService service;

    /**
     * Service to manage count.
     */
    @Autowired
    private CountService countService;

    /**
     * Count field of entry for restricted value.
     *
     * @param countBody
     *            CountDTO
     * @param projectsBody
     *            Project's filter.
     * @return Resource to contains entries and total of this.
     */
    @GET
    @PermitAll
    @Path("/count")
    public Response count(@BeanParam final CountDTO countBody, @BeanParam final SearchProjectViewDTO projectsBody) {
        LOGGER.info("Received GET request to count projects with count DTO {} and search DTO {}", countBody,
                projectsBody);
        CountDTO countDTO = new BeanMapper<>(CountDTO.class).apply(countBody);
        SearchProjectViewDTO entriesDTO = new BeanMapper<>(SearchProjectViewDTO.class).apply(projectsBody);
        return Response.ok(this.countService.count(ProjectView.class, countDTO, entriesDTO)).build();
    }

    /**
     * Endpoint to return a specific project.
     *
     * @param id
     *            Deployment's id.
     * @return Deployment.
     */
    @GET
    @Path("/{id}")
    @PermitAll
    public final Response getProject(@PathParam("id") final Long id) {
        LOGGER.info("Receive GET request to get project with id {}", id);
        return Response.ok(this.service.load(id)).build();
    }

    /**
     * Endpoint to return all projects.
     *
     * @param dto
     *            Project's filter.
     * @return List of project name.
     */
    @GET
    @PermitAll
    public final Response getProjects(final @BeanParam SearchProjectViewDTO dto) {
        LOGGER.info("Receive GET request to get all projects with {}", dto);
        return Response.ok(this.service.findAll(dto)).build();
    }

    /**
     * Create project.
     *
     * @param dto
     *            Project DTO.
     * @return Project.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({ UserRoleType.ADMIN, UserRoleType.PROJECT_MANAGER })
    public final Response createProject(final NewProjectRecord dto) {
        LOGGER.info("Receive POST request to create project with dto {}", dto);
        return Response.ok(this.service.save(dto)).status(Status.CREATED).build();
    }

    /**
     * Delete project in database.
     *
     * @param id
     *            Id of project to search.
     * @return No content.
     */
    @DELETE
    @Operation(summary = "Delete project in database.",
            responses = { @ApiResponse(responseCode = "204"), @ApiResponse(responseCode = "404",
                    content = @Content(mediaType = "application/json", schema = @Schema(allOf = { Error.class })),
                    description = "Error on unknown project id.") })
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @RolesAllowed({ UserRoleType.ADMIN, UserRoleType.PROJECT_MANAGER })
    public Response deleteProject(@PathParam("id") final Long id) {
        LOGGER.info("Receive DELETE request to delete project with id {}", id);
        this.service.delete(id);
        return Response.noContent().build();
    }
}
