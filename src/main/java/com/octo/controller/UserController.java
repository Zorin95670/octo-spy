package com.octo.controller;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.octo.model.authentication.UserRoleType;
import com.octo.model.dto.user.FullUserDTO;
import com.octo.model.entity.User;
import com.octo.service.UserService;
import com.octo.utils.http.UserMapper;

import io.swagger.v3.oas.annotations.servers.Server;

/**
 * User controller.
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Controller
@Server(url = "/octo-spy/api")
public class UserController {

    /**
     * User service.
     */
    @Autowired
    private UserService service;

    /**
     * Get user information.
     *
     * @param requestContext
     *            Request context to get default user login.
     * @return Response with user information.
     */
    @GET
    @Path("/me")
    @RolesAllowed(UserRoleType.ALL)
    public final Response getMyInformations(@Context final ContainerRequestContext requestContext) {
        User user = new UserMapper().apply(requestContext);
        FullUserDTO fullUser = new FullUserDTO();
        fullUser.setUser(service.getUser(user.getLogin()));
        fullUser.setRoles(service.getUserRoles(user.getLogin()));
        return Response.ok(fullUser).build();
    }
}
