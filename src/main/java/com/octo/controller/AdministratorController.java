package com.octo.controller;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.octo.model.authentication.UserRoleType;
import com.octo.service.UserService;

import io.swagger.v3.oas.annotations.servers.Server;

/**
 * User controller.
 */
@Path("/administrator")
@Produces(MediaType.APPLICATION_JSON)
@Controller
@Server(url = "/octo-spy/api")
public class AdministratorController {

    /**
     * User service.
     */
    @Autowired
    private UserService service;

    /**
     * Update administrator password.
     *
     * @param encodedPassword
     *            Encoded password.
     * @return No content response.
     */
    @PUT
    @Path("/password")
    @RolesAllowed(UserRoleType.ADMIN)
    public final Response updatePassword(final String encodedPassword) {
        this.service.updateDefaultAdminitratorPassword(encodedPassword);
        return Response.noContent().build();
    }

    /**
     * Update administrator email.
     *
     * @param email
     *            Email.
     * @return No content response.
     */
    @PUT
    @Path("/email")
    @RolesAllowed(UserRoleType.ADMIN)
    public final Response updateEmail(final String email) {
        this.service.updateDefaultAdminitratorEmail(email);
        return Response.noContent().build();
    }
}
