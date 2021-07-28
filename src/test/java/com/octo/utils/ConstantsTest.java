package com.octo.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Constructor;

import org.junit.jupiter.api.Test;

class ConstantsTest {

    @Test
    void testConstructor() {
        Exception exception = null;
        try {
            final Constructor<Constants> c = Constants.class.getDeclaredConstructor();
            c.setAccessible(true);
            c.newInstance();
        } catch (final Exception e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(UnsupportedOperationException.class, exception.getCause().getClass());
    }
}
