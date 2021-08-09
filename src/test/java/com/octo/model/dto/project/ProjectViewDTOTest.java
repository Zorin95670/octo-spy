package com.octo.model.dto.project;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ProjectViewDTOTest {

    @Test
    void testGetterAndSetter() {
        ProjectViewDTO dto = new ProjectViewDTO();

        assertNull(dto.getMasterProject());
        assertNull(dto.getColor());

        dto.setMasterProject("masterProject");
        dto.setColor("color");

        assertEquals("masterProject", dto.getMasterProject());
        assertEquals("color", dto.getColor());
    }
}
