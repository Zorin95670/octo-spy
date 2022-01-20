package com.octo.controller;

import com.octo.service.DeploymentReportViewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Map;

/**
 * Controller to manage report.
 *
 * @author Vincent Moittié
 */
@Path("/reports")
@Produces(MediaType.APPLICATION_JSON)
@Controller
public class ReportController {

    /**
     * Logger.
     **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

    /**
     * Service to manage count.
     */
    @Autowired
    private DeploymentReportViewService service;

    /**
     * Count field of deployments report for restricted value.
     *
     * @param uriInfo Uri info to get query parameters.
     * @param fields  Field names to count.
     * @return Resource to contains deployments and total of this.
     */
    @GET
    @PermitAll
    @Path("/deployments")
    public Response count(final @Context UriInfo uriInfo,
                          final @Valid @NotEmpty @QueryParam("fields") List<String> fields) {
        Map<String, String> filters = ControllerHelper.getFilters(uriInfo);
        LOGGER.info("Received GET request to count report deployments with {} and {}.", fields, filters);
        return Response.ok(this.service.count(filters, fields)).build();
    }
}
