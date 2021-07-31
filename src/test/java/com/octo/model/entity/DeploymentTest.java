package com.octo.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

class DeploymentTest {

    @Test
    void testGetterAndSetter() {
        Deployment entity = new Deployment();

        assertFalse(entity.isAlive());
        assertNull(entity.getId());
        assertNull(entity.getEnvironment());
        assertNull(entity.getProject());
        assertNull(entity.getVersion());
        assertNull(entity.getClient());

        entity.setAlive(true);
        entity.setClient("client");
        entity.setEnvironment(new Environment());
        entity.setProject(new Project());
        entity.setId(1L);
        entity.setVersion("version");

        assertTrue(entity.isAlive());
        assertEquals(Long.valueOf(1L), entity.getId());
        assertNotNull(entity.getEnvironment());
        assertNotNull(entity.getProject());
        assertEquals("version", entity.getVersion());
        assertEquals("client", entity.getClient());
    }
}
