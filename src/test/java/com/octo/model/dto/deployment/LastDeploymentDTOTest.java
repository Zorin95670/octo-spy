package com.octo.model.dto.deployment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.Instant;

import org.junit.jupiter.api.Test;

class LastDeploymentDTOTest {

    @Test
    void testGetterAndSetter() {
        Timestamp time = Timestamp.from(Instant.now());
        LastDeploymentDTO dto = new LastDeploymentDTO();

        assertNull(dto.getClient());
        assertNull(dto.getEnvironment());
        assertNull(dto.getId());
        assertNull(dto.getProject());
        assertNull(dto.getMasterProject());
        assertNull(dto.getMasterProjectColor());
        assertNull(dto.getVersion());
        assertNull(dto.getColor());
        assertNull(dto.getInsertDate());
        assertFalse(dto.isInProgress());
        assertFalse(dto.getOnMasterProject());

        dto.setClient("client");
        dto.setEnvironment("environment");
        dto.setId(1L);
        dto.setProject("project");
        dto.setMasterProject("master");
        dto.setMasterProjectColor("masterProjectColor");
        dto.setVersion("version");
        dto.setColor("color");
        dto.setInsertDate(time);
        dto.setInProgress(true);
        dto.setAlive(true);

        assertEquals("client", dto.getClient());
        assertEquals("environment", dto.getEnvironment());
        assertEquals(Long.valueOf(1L), dto.getId());
        assertEquals("project", dto.getProject());
        assertEquals("master", dto.getMasterProject());
        assertEquals("masterProjectColor", dto.getMasterProjectColor());
        assertEquals("version", dto.getVersion());
        assertEquals("color", dto.getColor());
        assertEquals(time, dto.getInsertDate());
        assertTrue(dto.isInProgress());
        assertTrue(dto.getOnMasterProject());

        dto.setInsertDate(null);
        assertNull(dto.getInsertDate());
    }
}
