package com.octo.api.handler;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.octo.models.exception.ControllerException;

/**
 * Handle exception and send appropriate response.
 *
 * @author vmoittie
 *
 */
@Provider
public class ExceptionHandler implements ExceptionMapper<ControllerException> {

    @Override
    public final Response toResponse(final ControllerException exception) {
        return Response.status(exception.getStatus()).entity(exception.getError()).type(MediaType.APPLICATION_JSON)
                .build();
    }

}
