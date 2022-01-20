package com.octo.controller.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("unit")
class GlobalExceptionHandlerTest {

    @Test
    void toResponseTest() throws JsonProcessingException {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        GlobalException exception = new GlobalException(ErrorType.WRONG_VALUE, null, null);

        Response response = handler.toResponse(exception);
        assertNotNull(response);
        assertEquals(exception.getStatus().getStatusCode(), response.getStatus());
        assertEquals(new ObjectMapper().writeValueAsString(exception.getError()), response.getEntity());
    }
}
