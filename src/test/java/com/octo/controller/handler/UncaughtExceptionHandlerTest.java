package com.octo.controller.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

public class UncaughtExceptionHandlerTest {

    @Test
    public void toResponseTest() {
        final UncaughtExceptionHandler handler = new UncaughtExceptionHandler();
        final Exception exception = new Exception("test");

        final Response response = handler.toResponse(exception);
        assertNotNull(response);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }

}
