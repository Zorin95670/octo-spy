package com.octo.controller.handler;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Handle global exception and send appropriate response.
 *
 * @author Vincent Moittié
 */
@Provider
public class GlobalExceptionHandler implements ExceptionMapper<GlobalException> {

    /**
     * Logger.
     **/
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    public final Response toResponse(final GlobalException exception) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        LOGGER.debug("General error", exception);
        try {
            return Response.status(exception.getStatus())
                    .entity(mapper.writeValueAsString(exception.getError()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            throw new GlobalException(exception, ErrorType.INTERNAL_ERROR, exception.getError().getField());
        }
    }

}
