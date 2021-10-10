package com.octo.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;

class EnvironmentTest {

    @Test
    void testGetterSetter() {
        Environment environment = new Environment();

        assertNull(environment.getId());
        assertNull(environment.getName());
        assertEquals(0, environment.getPosition());

        environment.setId(1L);
        environment.setName("name");
        environment.setPosition(1);

        assertEquals(Long.valueOf(1L), environment.getId());
        assertEquals("name", environment.getName());
        assertEquals(1, environment.getPosition());
    }
}
