package com.octo.model.dto.project;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ProjectViewDTOTest {

    @Test
    public void testGetterAndSetter() {
        ProjectViewDTO dto = new ProjectViewDTO();

        assertNull(dto.getMasterProject());

        dto.setMasterProject("masterProject");

        assertEquals("masterProject", dto.getMasterProject());
    }
}
