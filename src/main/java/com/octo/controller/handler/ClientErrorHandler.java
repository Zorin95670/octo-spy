package com.octo.controller.handler;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Handle all client error exception and send appropriate response.
 *
 * @author vmoittie
 *
 */
@Provider
public class ClientErrorHandler implements ExceptionMapper<WebApplicationException> {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientErrorHandler.class);

    @Override
    public final Response toResponse(final WebApplicationException exception) {
        LOGGER.error("Client error", exception);
        final ObjectNode json = JsonNodeFactory.instance.objectNode();
        if (exception.getMessage() != null) {
            json.put("message", exception.getMessage());
        } else {
            json.put("message", "Client error.");
        }
        return Response.fromResponse(exception.getResponse()).entity(json).type(MediaType.APPLICATION_JSON).build();
    }

}
