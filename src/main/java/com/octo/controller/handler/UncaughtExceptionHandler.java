package com.octo.controller.handler;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.octo.models.error.ErrorDTO;
import com.octo.models.error.ErrorType;

/**
 * Handle all uncaught exception and send appropriate response.
 */
public class UncaughtExceptionHandler implements ExceptionMapper<Throwable> {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(UncaughtExceptionHandler.class);

    @Override
    public final Response toResponse(final Throwable exception) {
        LOGGER.error("Uncatch error", exception);
        final ErrorDTO error = new ErrorDTO(ErrorType.INTERNAL_ERROR.getMessage(), null, null, exception);

        return Response.status(ErrorType.INTERNAL_ERROR.getStatus()).entity(error).type(MediaType.APPLICATION_JSON)
                .build();
    }
}
