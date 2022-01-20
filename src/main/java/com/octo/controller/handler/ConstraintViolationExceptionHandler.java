package com.octo.controller.handler;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.octo.model.error.ErrorDTO;
import com.octo.model.error.ErrorType;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Iterator;
import java.util.Optional;

@Provider
public class ConstraintViolationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public final Response toResponse(final ConstraintViolationException exception) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        Optional<ConstraintViolation<?>> opt = exception.getConstraintViolations().stream().findFirst();

        if (opt.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        ConstraintViolation<?> violation = opt.get();
        ErrorDTO error = new ErrorDTO();

        error.setField(getLastPath(violation.getPropertyPath()));
        if (violation.getInvalidValue() == null) {
            error.setMessage(ErrorType.EMPTY_VALUE.getMessage());
        } else {
            error.setMessage(ErrorType.WRONG_VALUE.getMessage());
            error.setValue(violation.getInvalidValue().toString());
        }

        try {
            return Response.status(Response.Status.BAD_REQUEST).entity(mapper.writeValueAsString(error))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (JsonProcessingException e) {
            throw new ClientErrorException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }

    /**
     * Get last path.
     *
     * @param path Path to extract.
     * @return Path.
     */
    public String getLastPath(final Path path) {
        Iterator<Path.Node> iterator = path.iterator();
        Path.Node node = iterator.next();

        while (iterator.hasNext()) {
            node = iterator.next();
        }
        return node.getName();
    }
}
