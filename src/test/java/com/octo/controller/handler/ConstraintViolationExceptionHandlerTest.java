package com.octo.controller.handler;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("unit")
class ConstraintViolationExceptionHandlerTest {

    private final Path defaultPath = () -> {
        Path.Node node1 = Mockito.mock(Path.Node.class);
        Path.Node node2 = Mockito.mock(Path.Node.class);

        Mockito.when(node1.getName()).thenReturn("test1");
        Mockito.when(node2.getName()).thenReturn("test2");

        return List.of(node1, node2).iterator();
    };

    @Test
    void toResponseShouldReturnBadRequestOnEmptyConstraints() {
        ConstraintViolationExceptionHandler handler = new ConstraintViolationExceptionHandler();
        ConstraintViolationException exception = new ConstraintViolationException(Collections.emptySet());
        Response response = handler.toResponse(exception);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void toResponseShouldReturnBadRequestOnEmptyValue() {
        ConstraintViolationExceptionHandler handler = new ConstraintViolationExceptionHandler();
        ConstraintViolation violation = Mockito.mock(ConstraintViolation.class);
        Mockito.when(violation.getPropertyPath()).thenReturn(defaultPath);
        Set<ConstraintViolation<String>> set = new HashSet<>();
        set.add(violation);
        ConstraintViolationException exception = new ConstraintViolationException(set);
        Response response = handler.toResponse(exception);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void toResponseShouldReturnBadRequest() {
        ConstraintViolationExceptionHandler handler = new ConstraintViolationExceptionHandler();
        ConstraintViolation violation = Mockito.mock(ConstraintViolation.class);
        Mockito.when(violation.getPropertyPath()).thenReturn(defaultPath);
        Mockito.when(violation.getInvalidValue()).thenReturn("test");
        Set<ConstraintViolation<String>> set = new HashSet<>();
        set.add(violation);
        ConstraintViolationException exception = new ConstraintViolationException(set);
        Response response = handler.toResponse(exception);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }
}
