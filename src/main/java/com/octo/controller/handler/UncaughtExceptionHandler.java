package com.octo.controller.handler;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.octo.model.error.ErrorType;
import com.octo.model.error.Error;

/**
 * Handle all uncaught exception and send appropriate response.
 *
 * @author vmoittie
 *
 */
public class UncaughtExceptionHandler implements ExceptionMapper<Throwable> {

    @Override
    public final Response toResponse(final Throwable exception) {
        final Error error = new Error(ErrorType.INTERNAL_ERROR.getMessage(), null, null, exception);

        return Response.status(ErrorType.INTERNAL_ERROR.getStatus()).entity(error).type(MediaType.APPLICATION_JSON)
                .build();
    }
}
