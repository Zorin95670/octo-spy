package com.octo.controller.handler;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.octo.model.exception.OctoException;

/**
 * Handle octo exception and send appropriate response.
 *
 * @author vmoittie
 *
 */
@Provider
public class OctoExceptionHandler implements ExceptionMapper<OctoException> {

    @Override
    public final Response toResponse(final OctoException exception) {
        return Response.status(exception.getStatus()).entity(exception.getError()).type(MediaType.APPLICATION_JSON)
                .build();
    }

}
