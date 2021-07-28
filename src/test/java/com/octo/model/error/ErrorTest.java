package com.octo.model.error;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ErrorTest {

    @Test
    void getterSetterTest() {
        final ErrorDTO error = new ErrorDTO();

        assertNull(error.getMessage());
        assertNull(error.getField());
        assertNull(error.getValue());
        assertNull(error.getCause());

        error.setMessage("message");
        assertEquals("message", error.getMessage());

        error.setField("field");
        assertEquals("field", error.getField());

        error.setValue("value");
        assertEquals("value", error.getValue());

        error.setCause("cause");
        assertEquals("cause", error.getCause());

        Exception exception = null;
        error.setCause(exception);
        assertNull(error.getCause());

        exception = new Exception("cause");
        error.setCause(exception);
        assertEquals("cause", error.getCause());
    }
}
