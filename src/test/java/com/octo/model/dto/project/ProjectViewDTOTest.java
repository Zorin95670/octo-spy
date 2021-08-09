package com.octo.model.dto.project;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ProjectViewDTOTest {

    @Test
    void testGetterAndSetter() {
        ProjectViewDTO dto = new ProjectViewDTO();

        assertNull(dto.getMasterProject());
        assertNull(dto.getColor());
        assertFalse(dto.getIsMaster());

        dto.setMasterProject("masterProject");
        dto.setColor("color");
        dto.setIsMaster(true);

        assertEquals("masterProject", dto.getMasterProject());
        assertEquals("color", dto.getColor());
        assertTrue(dto.getIsMaster());
    }
}
