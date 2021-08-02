package com.octo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.octo.model.authentication.UserRoleType;
import com.octo.model.common.Resource;
import com.octo.model.dto.alert.AlertRecord;
import com.octo.service.UserService;

import io.swagger.v3.oas.annotations.servers.Server;

/**
 * Alerts controller.
 */
@Path("/alerts")
@Produces(MediaType.APPLICATION_JSON)
@Controller
@Server(url = "/octo-spy/api")
public class AlertsController {

    /**
     * User service.
     */
    @Autowired
    private UserService service;

    /**
     * Get all applications alerts.
     *
     * @return List of alerts.
     */
    @GET
    @RolesAllowed(UserRoleType.ADMIN)
    public final Response getAlerts() {
        List<AlertRecord> alerts = new ArrayList<>();

        if (!service.isSecureAdministrator()) {
            alerts.add(new AlertRecord("warning", "security",
                    "Administrator's password is not secure, please change it."));
        }

        if (!service.isValidAdministratorEmail()) {
            alerts.add(new AlertRecord("critical", "security", "Administrator's email is not set, please change it."));
        }

        if (alerts.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(new Resource<>(Long.valueOf(alerts.size()), alerts, 0, alerts.size())).build();
    }

}
