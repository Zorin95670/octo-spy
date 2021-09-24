package com.octo.controller;

import java.security.NoSuchAlgorithmException;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.BeanParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.octo.model.authentication.UserRoleType;
import com.octo.model.dto.user.FullUserDTO;
import com.octo.model.dto.user.token.SearchUserTokenDTO;
import com.octo.model.entity.User;
import com.octo.service.UserService;
import com.octo.utils.http.UserMapper;

/**
 * User controller.
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Controller
public class UserController {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

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
        LOGGER.info("Received GET request to retrieve user information of {}.", user.getLogin());
        FullUserDTO fullUser = new FullUserDTO();
        fullUser.setUser(service.getUser(user.getLogin()));
        fullUser.setRoles(service.getUserRoles(user.getLogin()));
        return Response.ok(fullUser).build();
    }

    /**
     * Get user token.
     *
     * @param requestContext
     *            Request context to get default user login.
     * @return Response with user token.
     */
    @GET
    @Path("/token")
    @RolesAllowed(UserRoleType.ADMIN)
    public final Response getToken(@Context final ContainerRequestContext requestContext) {
        User user = new UserMapper().apply(requestContext);
        LOGGER.info("Received GET request to retrieve user tokens of {}.", user.getLogin());
        ArrayNode tokens = JsonNodeFactory.instance.arrayNode();
        service.getUserToken(user.getLogin()).forEach(tokens::add);
        return Response.ok(tokens).build();
    }

    /**
     * Create token.
     *
     * @param requestContext
     *            Request context to get default user login.
     * @param body
     *            Token name.
     * @return Response with generated token.
     * @throws NoSuchAlgorithmException
     *             On no secure random algorithm found.
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
     * @param requestContext
     *            Request context to get default user login.
     * @param dto
     *            User token filter
     * @return No content response.
     */
    @DELETE
    @Path("/token")
    @RolesAllowed(UserRoleType.ADMIN)
    public final Response deleteToken(@Context final ContainerRequestContext requestContext,
            final @BeanParam SearchUserTokenDTO dto) {
        User user = new UserMapper().apply(requestContext);
        LOGGER.info("Received DELETE request to delete user token for {}, with {}.", user.getLogin(), dto);
        service.deleteToken(user.getLogin(), dto);
        return Response.noContent().build();
    }

}
