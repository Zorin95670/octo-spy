package com.octo.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.Instant;

import org.junit.Test;

public class LastDeploymentViewTest {

    @Test
    public void testGetterAndSetter() {
        Timestamp time = Timestamp.from(Instant.now());
        LastDeploymentView entity = new LastDeploymentView();

        assertNull(entity.getClient());
        assertNull(entity.getEnvironment());
        assertNull(entity.getId());
        assertNull(entity.getProject());
        assertNull(entity.getVersion());
        assertNull(entity.getInsertDate());
        assertFalse(entity.isInProgress());

        entity.setClient("client");
        entity.setEnvironment("environment");
        entity.setId(1L);
        entity.setProject("project");
        entity.setVersion("version");
        entity.setInsertDate(time);
        entity.setInProgress(true);

        assertEquals("client", entity.getClient());
        assertEquals("environment", entity.getEnvironment());
        assertEquals(Long.valueOf(1L), entity.getId());
        assertEquals("project", entity.getProject());
        assertEquals("version", entity.getVersion());
        assertEquals(time, entity.getInsertDate());
        assertTrue(entity.isInProgress());

        entity.setInsertDate(null);
        assertNull(entity.getInsertDate());
    }
}
