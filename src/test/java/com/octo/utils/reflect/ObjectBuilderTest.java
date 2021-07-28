package com.octo.utils.reflect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.octo.helpers.EntityHelpers;
import com.octo.model.error.GlobalException;

class ObjectBuilderTest {

    @Test
    void testSetWrongField() {
        GlobalException exception = null;
        try {
            ObjectBuilder.init(EntityHelpers.class).setField("wrong field", null);
        } catch (final GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @Test
    void testSetWrongFieldValue() {
        GlobalException exception = null;
        try {
            ObjectBuilder.init(EntityHelpers.class).setField("id", "a");
        } catch (final GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @Test
    void testBadConstructor() {
        GlobalException exception = null;
        try {
            ObjectBuilder.init(EntityHelpers.class, new Class<?>[] { String.class }, new Object[] { "" });
        } catch (final GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @Test
    void testSetField() {
        final EntityHelpers entry = ObjectBuilder.init(EntityHelpers.class).setField("id", 1L).build();

        assertNotNull(entry);
        assertEquals(Long.valueOf(1l), entry.getId());
    }
}
