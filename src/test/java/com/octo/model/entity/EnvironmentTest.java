package com.octo.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.octo.model.entity.Environment;

public class EnvironmentTest {

    @Test
    public void testGetterSetter() {
        Environment environment = new Environment();

        assertNull(environment.getId());
        assertNull(environment.getName());
        assertNull(environment.getInsertDate());
        assertNull(environment.getUpdateDate());

        environment.setId(1L);
        environment.setName("name");

        assertEquals(Long.valueOf(1L), environment.getId());
        assertEquals("name", environment.getName());
    }
}
