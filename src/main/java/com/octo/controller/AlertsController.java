package com.octo.controller;

import com.octo.model.alert.Alert;
import com.octo.model.common.UserRoleType;
import com.octo.service.AlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Alerts controller.
 */
@Path("/alerts")
@Produces(MediaType.APPLICATION_JSON)
@Controller
public class AlertsController {

    /**
     * Logger.
     **/
    private static final Logger LOGGER = LoggerFactory.getLogger(AlertsController.class);

    /**
     * Alert service.
     */
    @Autowired
    private AlertService service;

    /**
     * Get all applications alerts.
     *
     * @return Alerts paginated.
     */
    @GET
    @RolesAllowed(UserRoleType.ADMIN)
    public final Response getAlerts() {
        LOGGER.info("Receive GET request to get all alerts.");
        Page<Alert> alerts = new PageImpl<>(service.getAlerts());
        return Response.status(ControllerHelper.getStatus(alerts)).entity(alerts).build();
    }

}
