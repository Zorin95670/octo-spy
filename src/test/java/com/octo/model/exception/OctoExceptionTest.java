package com.octo.model.exception;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.octo.model.exception.OctoException;

public class OctoExceptionTest {

    @Test
    public void test() {
        final OctoException exception = new OctoException(null) {
            private static final long serialVersionUID = 1L;
        };

        assertNull(exception.getStatus());
        assertNotNull(exception.getError());
    }
}
