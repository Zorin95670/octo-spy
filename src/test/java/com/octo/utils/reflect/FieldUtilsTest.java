package com.octo.utils.reflect;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class FieldUtilsTest {

    @Test
    void testConstructor() {
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
    void testClassHasField() {
        class Test {
            @SuppressWarnings("unused") // Used in tests below.
            private String test;
        }

        assertFalse(FieldUtils.hasField(Test.class, "bad"));
        assertTrue(FieldUtils.hasField(Test.class, "test"));
    }

    @Test
    void testSuperClassHasField() {
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
