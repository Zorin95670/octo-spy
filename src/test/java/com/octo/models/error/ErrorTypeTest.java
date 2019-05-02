package com.octo.models.error;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ErrorTypeTest {

    @Test
    public void enumTest() {
        assertEquals("An unknow error has appear.", ErrorType.INTERNAL.getMessage());
    }
}
