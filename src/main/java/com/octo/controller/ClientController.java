package com.octo.controller;

import com.octo.controller.model.QueryFilter;
import com.octo.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Client controller.
 *
 * @author Vincent Moittié
 */
@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
@Controller
public class ClientController {

    /**
     * Logger.
     **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    /**
     * Client service.
     */
    @Autowired
    private ClientService service;

    /**
     * Get all clients.
     *
     * @param queryFilter Sort and pagination options.
     * @return Client's names paginated.
     */
    @GET
    @PermitAll
    public final Response getClients(final @BeanParam @Valid QueryFilter queryFilter) {
        LOGGER.info("Receive GET request to get all clients with {}", queryFilter);
        Page<String> clients = this.service.findAll(queryFilter.getPagination());
        return Response.status(ControllerHelper.getStatus(clients)).entity(clients).build();
    }
}
