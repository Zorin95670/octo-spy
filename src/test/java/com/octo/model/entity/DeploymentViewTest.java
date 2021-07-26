package com.octo.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.Instant;

import org.junit.jupiter.api.Test;

public class DeploymentViewTest {

    @Test
    public void testGetterAndSetter() {
        Timestamp time = Timestamp.from(Instant.now());
        DeploymentView entity = new DeploymentView();

        assertNull(entity.getClient());
        assertNull(entity.getEnvironment());
        assertNull(entity.getId());
        assertNull(entity.getProject());
        assertNull(entity.getMasterProject());
        assertNull(entity.getVersion());
        assertNull(entity.getInsertDate());
        assertFalse(entity.isInProgress());
        assertFalse(entity.isAlive());

        entity.setClient("client");
        entity.setEnvironment("environment");
        entity.setId(1L);
        entity.setProject("project");
        entity.setMasterProject("master");
        entity.setVersion("version");
        entity.setInsertDate(time);
        entity.setInProgress(true);
        entity.setAlive(true);

        assertEquals("client", entity.getClient());
        assertEquals("environment", entity.getEnvironment());
        assertEquals(Long.valueOf(1L), entity.getId());
        assertEquals("project", entity.getProject());
        assertEquals("master", entity.getMasterProject());
        assertEquals("version", entity.getVersion());
        assertEquals(time, entity.getInsertDate());
        assertTrue(entity.isInProgress());
        assertTrue(entity.isAlive());

        entity.setInsertDate(null);
        assertNull(entity.getInsertDate());
    }
}
