package com.octo.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;

class ProjectTest {

    @Test
    void testGetterAndSetter() {
        Project entity = new Project();

        assertNull(entity.getId());
        assertNull(entity.getName());

        entity.setId(1L);
        entity.setName("name");

        assertEquals(Long.valueOf(1L), entity.getId());
        assertEquals("name", entity.getName());
    }
}
