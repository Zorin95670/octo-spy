package com.octo.model.dto.project;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SearchProjectViewDTOTest {

    @Test
    public void testGetterAndSetter() {
        SearchProjectViewDTO dto = new SearchProjectViewDTO();

        assertNull(dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getMasterProject());

        dto.setId("id");
        dto.setName("name");
        dto.setMasterProject("masterProject");

        assertEquals("id", dto.getId());
        assertEquals("name", dto.getName());
        assertEquals("masterProject", dto.getMasterProject());
    }
}
