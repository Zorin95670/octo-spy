package com.octo.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class EnvironmentTest {

    @Test
    public void testGetterSetter() {
        Environment environment = new Environment();

        assertNull(environment.getId());
        assertNull(environment.getName());

        environment.setId(1L);
        environment.setName("name");

        assertEquals(Long.valueOf(1L), environment.getId());
        assertEquals("name", environment.getName());
    }
}
