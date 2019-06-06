package com.octo.model.error;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ErrorTypeTest {

    @Test
    public void enumTest() {
        assertNotNull(ErrorType.INTERNAL_ERROR.getMessage());
        assertNotNull(ErrorType.INTERNAL_ERROR.getStatus());
    }
}
