package com.octo.model.authentication;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Constructor;

import org.junit.jupiter.api.Test;

class UserRoleTypeTest {

    @Test
    void testConstructor() {

        Exception exception = null;
        try {
            final Constructor<UserRoleType> c = UserRoleType.class.getDeclaredConstructor();
            c.setAccessible(true);
            c.newInstance();
        } catch (final Exception e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(IllegalStateException.class, exception.getCause().getClass());
    }
}
