package com.octo.model.error;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ErrorTest {

    @Test
    public void getterSetterTest() {
        final Error error = new Error();

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

        Exception exception = new Exception("test");
        error.setCause(exception);
        assertEquals(exception.getMessage(), error.getCause());
    }
}
