package com.octo.model.error;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.Test;

public class GlobalExceptionTest {

    @Test
    public void test() {
        Throwable throwable = new Throwable();
        GlobalException exception = new GlobalException(ErrorType.INTERNAL_ERROR, null);
        assertTrue(exception.needToBeLogged());
        assertEquals(Status.INTERNAL_SERVER_ERROR, exception.getStatus());
        assertNotNull(exception.getError());

        exception = new GlobalException(ErrorType.INTERNAL_ERROR, "field", "value");
        assertTrue(exception.needToBeLogged());
        assertEquals(Status.INTERNAL_SERVER_ERROR, exception.getStatus());
        assertNotNull(exception.getError());

        exception = new GlobalException(throwable, ErrorType.INTERNAL_ERROR, "field");
        assertTrue(exception.needToBeLogged());
        assertEquals(Status.INTERNAL_SERVER_ERROR, exception.getStatus());
        assertNotNull(exception.getError());

        exception = new GlobalException(throwable, ErrorType.INTERNAL_ERROR, "field", "value");
        assertTrue(exception.needToBeLogged());
        assertEquals(Status.INTERNAL_SERVER_ERROR, exception.getStatus());
        assertNotNull(exception.getError());

        exception = new GlobalException(ErrorType.INTERNAL_ERROR, "field");
        assertTrue(exception.needToBeLogged());
        assertEquals(Status.INTERNAL_SERVER_ERROR, exception.getStatus());
        assertNotNull(exception.getError());

        exception = new GlobalException(ErrorType.INTERNAL_ERROR, null, false);
        assertFalse(exception.needToBeLogged());
        assertEquals(Status.INTERNAL_SERVER_ERROR, exception.getStatus());
        assertNotNull(exception.getError());

        exception = new GlobalException(ErrorType.INTERNAL_ERROR, "field", "value", false);
        assertFalse(exception.needToBeLogged());
        assertEquals(Status.INTERNAL_SERVER_ERROR, exception.getStatus());
        assertNotNull(exception.getError());

        exception = new GlobalException(throwable, ErrorType.INTERNAL_ERROR, "field", false);
        assertFalse(exception.needToBeLogged());
        assertEquals(Status.INTERNAL_SERVER_ERROR, exception.getStatus());
        assertNotNull(exception.getError());

        exception = new GlobalException(throwable, ErrorType.INTERNAL_ERROR, "field", "value", false);
        assertFalse(exception.needToBeLogged());
        assertEquals(Status.INTERNAL_SERVER_ERROR, exception.getStatus());
        assertNotNull(exception.getError());

        exception = new GlobalException(ErrorType.INTERNAL_ERROR, "field", false);
        assertFalse(exception.needToBeLogged());
        assertEquals(Status.INTERNAL_SERVER_ERROR, exception.getStatus());
        assertNotNull(exception.getError());
    }
}
