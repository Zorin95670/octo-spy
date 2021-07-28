package com.octo.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.Instant;

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
        assertNull(entity.getInsertDate());
        assertNull(entity.getUpdateDate());

        entity.setAlive(true);
        entity.setClient("client");
        entity.setEnvironment(new Environment());
        entity.setProject(new Project());
        entity.setId(1L);
        entity.setInsertDate(Timestamp.from(Instant.ofEpochMilli(1L)));
        entity.setUpdateDate(Timestamp.from(Instant.ofEpochMilli(2L)));
        entity.setVersion("version");

        assertTrue(entity.isAlive());
        assertEquals(Long.valueOf(1L), entity.getId());
        assertNotNull(entity.getEnvironment());
        assertNotNull(entity.getProject());
        assertEquals("version", entity.getVersion());
        assertEquals("client", entity.getClient());
        assertEquals(Timestamp.from(Instant.ofEpochMilli(1L)), entity.getInsertDate());
        assertEquals(Timestamp.from(Instant.ofEpochMilli(2L)), entity.getUpdateDate());

        entity.setInsertDate(null);
        entity.setUpdateDate(null);
        assertNull(entity.getInsertDate());
        assertNull(entity.getUpdateDate());
    }

    @Test
    void testPrePersist() {
        Deployment entity = new Deployment();

        assertFalse(entity.isAlive());
        assertNull(entity.getId());
        assertNull(entity.getEnvironment());
        assertNull(entity.getVersion());
        assertNull(entity.getClient());
        assertNull(entity.getInsertDate());
        assertNull(entity.getUpdateDate());

        entity.prePersist();

        assertFalse(entity.isAlive());
        assertNull(entity.getId());
        assertNull(entity.getEnvironment());
        assertNull(entity.getVersion());
        assertNull(entity.getClient());
        assertNotNull(entity.getInsertDate());
        assertNull(entity.getUpdateDate());
    }
}
