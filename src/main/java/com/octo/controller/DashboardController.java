package com.octo.controller;

import com.octo.controller.model.QueryFilter;
import com.octo.model.common.UserRoleType;
import com.octo.model.dashboard.DashboardRecord;
import com.octo.persistence.model.Dashboard;
import com.octo.service.DashboardService;
import com.octo.utils.bean.BeanMapper;
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
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Dashboard controller.
 *
 * @author Vincent Moittié
 */
@Path("/dashboards")
@Produces(MediaType.APPLICATION_JSON)
@Controller
public class DashboardController {

    /**
     * Logger.
     **/
    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

    /**
     * Dashboard service.
     */
    @Autowired
    private DashboardService dashboardService;

    /**
     * Get dashboard by id.
     *
     * @param requestContext Request context to get default user login.
     * @param id             Dashboard id.
     * @return Dashboard.
     */
    @GET
    @Path("/{id}")
    @PermitAll
    public final Response getDashboard(@Context final ContainerRequestContext requestContext,
                                       @PathParam("id") @Valid @NotNull final Long id) {
        LOGGER.info("Receive GET request to get dashboard with id {}.", id);
        return Response.ok(this.dashboardService.load(ControllerHelper.getLogin(requestContext), id)).build();
    }

    /**
     * Get dashboards.
     *
     * @param requestContext Request context to get default user login.
     * @param uriInfo        Request URI information.
     * @param queryFilter    Sort and pagination option.
     * @return Dashboards paginated.
     */
    @GET
    @PermitAll
    public final Response getDashboards(@Context final ContainerRequestContext requestContext,
                                        final @Context UriInfo uriInfo,
                                        final @BeanParam @Valid QueryFilter queryFilter) {
        Map<String, String> filters = ControllerHelper.getFilters(uriInfo);
        LOGGER.info("Receive GET request to get dashboards with {}.", filters);
        final Page<Dashboard> resources = this.dashboardService.find(ControllerHelper.getLogin(requestContext),
                        filters, queryFilter.getPagination())
                .map(new BeanMapper<>(Dashboard.class));
        return Response.status(ControllerHelper.getStatus(resources)).entity(resources).build();
    }

    /**
     * Create dashboard.
     *
     * @param requestContext Request context to get default user login.
     * @param newDashboard   Dashboard record.
     * @return Dashboard.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRoleType.ADMIN, UserRoleType.TOKEN})
    public final Response createDashboard(@Context final ContainerRequestContext requestContext,
                                          final @Valid DashboardRecord newDashboard) {
        LOGGER.info("Receive POST request to create dashboard with {}.", newDashboard);
        Dashboard dashboard = this.dashboardService.save(ControllerHelper.getLogin(requestContext), newDashboard);
        return Response.ok(dashboard).status(Status.CREATED).build();
    }

    /**
     * Update a specific dashboard.
     *
     * @param requestContext Request context to get default user login.
     * @param id             Dashboard id.
     * @param dashboard      Dashboard record.
     * @return No content response.
     * @throws InvocationTargetException If the property accessor method throws an exception.
     * @throws IllegalAccessException    If the caller does not have access to the property accessor
     *                                   method.
     */
    @PUT
    @Path("/{id}")
    @RolesAllowed({UserRoleType.ADMIN, UserRoleType.TOKEN})
    public final Response updateDashboard(@Context final ContainerRequestContext requestContext,
                                          @PathParam("id") @Valid @NotNull final Long id,
                                          @Valid final DashboardRecord dashboard)
            throws IllegalAccessException, InvocationTargetException {
        LOGGER.info("Receive PUT request to update project with id {} and {}.", id, dashboard);
        this.dashboardService.update(ControllerHelper.getLogin(requestContext), id, dashboard);
        return Response.noContent().build();
    }

    /**
     * Delete dashboard in database.
     *
     * @param requestContext Request context to get default user login.
     * @param id             Dashboard id.
     * @return No content.
     */
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @RolesAllowed({UserRoleType.ADMIN})
    public Response deleteDashboard(@Context final ContainerRequestContext requestContext,
                                    @PathParam("id") @Valid @NotNull final Long id) {
        LOGGER.info("Receive DELETE request to delete dashboard with id {}.", id);
        this.dashboardService.delete(ControllerHelper.getLogin(requestContext), id);
        return Response.noContent().build();
    }
}
