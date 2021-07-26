package com.octo.controller.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;

public class GlobalExceptionHandlerTest {

    @Test
    public void toResponseTest() {
        final GlobalExceptionHandler handler = new GlobalExceptionHandler();
        GlobalException exception = new GlobalException(ErrorType.WRONG_VALUE, null, null);

        final Response response = handler.toResponse(exception);
        assertNotNull(response);
        assertEquals(exception.getStatus().getStatusCode(), response.getStatus());
        assertEquals(exception.getError(), response.getEntity());

    }
}
