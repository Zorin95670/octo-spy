package com.octo.model.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.octo.model.error.ErrorType;

public class OctoExceptionTest {

    @Test
    public void test() {
        final OctoException exception = new OctoException(ErrorType.BAD_VALUE, null, null);

        assertEquals(ErrorType.BAD_VALUE.getStatus(), exception.getStatus());
        assertNotNull(exception.getError());
    }
}
