package com.octo.api.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import com.octo.models.exception.ControllerException;

public class ExceptionHandlerTest {

    @Test
    public void toResponseTest() {
        final ExceptionHandler handler = new ExceptionHandler();
        ControllerException exception = new ControllerException(Status.BAD_REQUEST) {
            private static final long serialVersionUID = 1L;
        };

        final Response response = handler.toResponse(exception);
        assertNotNull(response);
        assertEquals(exception.getStatus().getStatusCode(), response.getStatus());
        assertEquals(exception.getError(), response.getEntity());

    }
}
