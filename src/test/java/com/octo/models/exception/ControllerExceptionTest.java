package com.octo.models.exception;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ControllerExceptionTest {

    @Test
    public void test() {
        final ControllerException exception = new ControllerException(null) {
            private static final long serialVersionUID = 1L;
        };

        assertNull(exception.getStatus());
        assertNotNull(exception.getError());
    }
}
