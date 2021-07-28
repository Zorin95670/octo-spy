package com.octo.utils.retry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class RetryExceptionTest {

    @Test
    void test() {
        RetryException exception = new RetryException("test");
        assertEquals("test", exception.getMessage());
        assertNull(exception.getCause());

        Exception expected = new Exception();
        exception = new RetryException(expected);
        assertNull(exception.getMessage());
        assertEquals(expected, exception.getCause());

        exception = new RetryException("test", expected);
        assertEquals("test", exception.getMessage());
        assertEquals(expected, exception.getCause());
    }

}
