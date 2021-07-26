package com.octo.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.octo.service.ClientService;

import io.swagger.v3.oas.annotations.servers.Server;

/**
 * Client controller.
 *
 * @author Vincent Moittié
 *
 */
@Path("/client")
@Produces(MediaType.APPLICATION_JSON)
@Controller
@Server(url = "/octo-spy/api")
public class ClientController {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    /**
     * Client service.
     */
    @Autowired
    private ClientService service;

    /**
     * Get all clients.
     *
     * @return List of project name.
     */
    @GET
    public final Response getClients() {
        LOGGER.info("Receive GET request to get all clients");
        return Response.ok(this.service.findAll()).build();
    }

}
