package com.octo.controller;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.octo.model.authentication.UserRoleType;
import com.octo.service.UserService;

/**
 * User controller.
 */
@Path("/administrator")
@Produces(MediaType.APPLICATION_JSON)
@Controller
public class AdministratorController {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorController.class);

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
        LOGGER.info("Received PUT request to update administrator password.");
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
        LOGGER.info("Received PUT request to update administrator email to {}.", email);
        this.service.updateDefaultAdminitratorEmail(email);
        return Response.noContent().build();
    }
}
