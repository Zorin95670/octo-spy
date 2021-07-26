package com.octo.controller.handler;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.octo.models.error.GlobalException;

/**
 * Handle global exception and send appropriate response. *
 */
@Provider
public class GlobalExceptionHandler implements ExceptionMapper<GlobalException> {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    public final Response toResponse(final GlobalException exception) {
        LOGGER.error("General error", exception);
        return Response.status(exception.getStatus()).entity(exception.getError()).type(MediaType.APPLICATION_JSON)
                .build();
    }

}
