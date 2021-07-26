package com.octo.utils.reflect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;

import org.junit.jupiter.api.Test;

public class FieldUtilsTest {

    @Test
    public void testConstructor() {

        Exception exception = null;
        try {
            final Constructor<FieldUtils> c = FieldUtils.class.getDeclaredConstructor();
            c.setAccessible(true);
            c.newInstance();
        } catch (final Exception e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(UnsupportedOperationException.class, exception.getCause().getClass());
    }

    @Test
    public void testClassHasField() {
        class Test {
            @SuppressWarnings("unused") // Used in tests below.
            private String test;
        }

        assertFalse(FieldUtils.hasField(Test.class, "bad"));
        assertTrue(FieldUtils.hasField(Test.class, "test"));
    }

    @Test
    public void testSuperClassHasField() {
        class Test {
            @SuppressWarnings("unused") // Used in tests below.
            private String test;
        }
        class SubTest extends Test {
            @SuppressWarnings("unused") // Used in tests below.
            private String subTest;
        }

        assertFalse(FieldUtils.hasField(SubTest.class, "bad"));
        assertTrue(FieldUtils.hasField(SubTest.class, "subTest"));
        assertTrue(FieldUtils.hasField(SubTest.class, "test"));
    }
}
