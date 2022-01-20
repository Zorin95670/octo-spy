package com.octo.controller;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.octo.model.common.UserRoleType;
import com.octo.model.user.UserDTO;
import com.octo.persistence.model.User;
import com.octo.security.UserMapper;
import com.octo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.security.NoSuchAlgorithmException;

/**
 * User controller.
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Controller
public class UserController {

    /**
     * Logger.
     **/
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /**
     * User service.
     */
    @Autowired
    private UserService service;

    /**
     * Get user information.
     *
     * @param requestContext Request context to get default user login.
     * @return Response with user information.
     */
    @GET
    @Path("/me")
    @RolesAllowed({UserRoleType.ALL, UserRoleType.TOKEN})
    public final Response getMyInformation(@Context final ContainerRequestContext requestContext) {
        User user = new UserMapper().apply(requestContext);
        LOGGER.info("Received GET request to retrieve user information of {}.", user.getLogin());
        UserDTO dto = new UserDTO();
        dto.setUser(service.getUser(user.getLogin()));
        dto.setRoles(service.getUserRoles(user.getLogin()));
        return Response.ok(dto).build();
    }

    /**
     * Get user token.
     *
     * @param requestContext Request context to get default user login.
     * @return Response with user token.
     */
    @GET
    @Path("/token")
    @RolesAllowed(UserRoleType.ADMIN)
    public final Response getToken(@Context final ContainerRequestContext requestContext) {
        User user = new UserMapper().apply(requestContext);
        LOGGER.info("Received GET request to retrieve user tokens of {}.", user.getLogin());
        Page<String> tokens = service.getUserToken(user.getLogin());
        return Response.status(ControllerHelper.getStatus(tokens)).entity(tokens).build();
    }

    /**
     * Create token.
     *
     * @param requestContext Request context to get default user login.
     * @param body           Token name.
     * @return Response with generated token.
     * @throws NoSuchAlgorithmException On no secure random algorithm found.
     */
    @POST
    @Path("/token")
    @RolesAllowed(UserRoleType.ADMIN)
    public final Response createToken(@Context final ContainerRequestContext requestContext, final String body)
            throws NoSuchAlgorithmException {
        User user = new UserMapper().apply(requestContext);
        LOGGER.info("Received POST request to create user token {} for {}.", body, user.getLogin());
        ObjectNode token = JsonNodeFactory.instance.objectNode();
        token.put("token", service.createToken(user.getLogin(), body));
        return Response.ok(token).status(Status.CREATED).build();
    }

    /**
     * Delete user token.
     *
     * @param requestContext Request context to get default user login.
     * @param name           Token name.
     * @return No content response.
     */
    @DELETE
    @Path("/token/{name}")
    @RolesAllowed(UserRoleType.ADMIN)
    public final Response deleteToken(@Context final ContainerRequestContext requestContext,
                                      @PathParam("name") @Valid @NotBlank final String name) {
        User user = new UserMapper().apply(requestContext);
        LOGGER.info("Received DELETE request to delete user token for {}, with {}.", user.getLogin(), name);
        service.deleteToken(user.getLogin(), name);
        return Response.noContent().build();
    }

}
