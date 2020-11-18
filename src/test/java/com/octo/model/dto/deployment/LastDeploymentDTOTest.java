package com.octo.model.dto.deployment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.Timestamp;
import java.time.Instant;

import org.junit.Test;

public class LastDeploymentDTOTest {

    @Test
    public void testGetterAndSetter() {
        Timestamp time = Timestamp.from(Instant.now());
        LastDeploymentDTO dto = new LastDeploymentDTO();

        assertNull(dto.getClient());
        assertNull(dto.getEnvironment());
        assertNull(dto.getId());
        assertNull(dto.getProject());
        assertNull(dto.getVersion());
        assertNull(dto.getInsertDate());

        dto.setClient("client");
        dto.setEnvironment("environment");
        dto.setId(1L);
        dto.setProject("project");
        dto.setVersion("version");
        dto.setInsertDate(time);

        assertEquals("client", dto.getClient());
        assertEquals("environment", dto.getEnvironment());
        assertEquals(Long.valueOf(1L), dto.getId());
        assertEquals("project", dto.getProject());
        assertEquals("version", dto.getVersion());
        assertEquals(time, dto.getInsertDate());

        dto.setInsertDate(null);
        assertNull(dto.getInsertDate());
    }
}
