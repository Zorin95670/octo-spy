package com.octo.controller.handler;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.octo.model.exception.OctoException;

/**
 * Handle octo exception and send appropriate response.
 *
 * @author vmoittie
 *
 */
@Provider
public class OctoExceptionHandler implements ExceptionMapper<OctoException> {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(OctoExceptionHandler.class);

    @Override
    public final Response toResponse(final OctoException exception) {
        LOGGER.error("General error", exception);
        return Response.status(exception.getStatus()).entity(exception.getError()).type(MediaType.APPLICATION_JSON)
                .build();
    }

}
