package com.octo.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.octo.model.authentication.UserRoleType;
import com.octo.model.common.Resource;
import com.octo.model.dto.alert.AlertRecord;
import com.octo.service.AlertService;

/**
 * Alerts controller.
 */
@Path("/alerts")
@Produces(MediaType.APPLICATION_JSON)
@Controller
public class AlertsController {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(AlertsController.class);

    /**
     * User service.
     */
    @Autowired
    private AlertService service;

    /**
     * Get all applications alerts.
     *
     * @return List of alerts.
     */
    @GET
    @RolesAllowed(UserRoleType.ADMIN)
    public final Response getAlerts() {
        LOGGER.info("Receive GET request to get all alerts.");
        List<AlertRecord> alerts = service.getAlerts();

        if (alerts.isEmpty()) {
            return Response.noContent().build();
        }

        return Response.ok(new Resource<>(Long.valueOf(alerts.size()), alerts, 0, alerts.size())).build();
    }

}
