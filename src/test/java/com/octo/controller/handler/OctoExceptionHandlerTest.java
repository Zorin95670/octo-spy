package com.octo.controller.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import com.octo.controller.handler.OctoExceptionHandler;
import com.octo.model.exception.OctoException;

public class OctoExceptionHandlerTest {

    @Test
    public void toResponseTest() {
        final OctoExceptionHandler handler = new OctoExceptionHandler();
        OctoException exception = new OctoException(Status.BAD_REQUEST) {
            private static final long serialVersionUID = 1L;
        };

        final Response response = handler.toResponse(exception);
        assertNotNull(response);
        assertEquals(exception.getStatus().getStatusCode(), response.getStatus());
        assertEquals(exception.getError(), response.getEntity());

    }
}
