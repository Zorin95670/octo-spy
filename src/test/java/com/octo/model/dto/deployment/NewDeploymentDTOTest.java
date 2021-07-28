package com.octo.model.dto.deployment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

class NewDeploymentDTOTest {

    @Test
    void testGetterAndSetter() {
        NewDeploymentDTO dto = new NewDeploymentDTO();

        assertFalse(dto.isAlive());
        assertNull(dto.getClient());
        assertNull(dto.getEnvironment());
        assertNull(dto.getVersion());
        assertFalse(dto.isInProgress());

        dto.setAlive(true);
        dto.setClient("client");
        dto.setEnvironment("environment");
        dto.setVersion("version");
        dto.setInProgress(true);

        assertTrue(dto.isAlive());
        assertEquals("client", dto.getClient());
        assertEquals("environment", dto.getEnvironment());
        assertEquals("version", dto.getVersion());
        assertTrue(dto.isInProgress());
    }
}
