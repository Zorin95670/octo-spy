package com.octo.model.dto.project;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SearchProjectViewDTOTest {

    @Test
    void testGetterAndSetter() {
        SearchProjectViewDTO dto = new SearchProjectViewDTO();

        assertNull(dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getMasterProject());
        assertNull(dto.getIsMaster());

        dto.setId("id");
        dto.setName("name");
        dto.setMasterProject("masterProject");
        dto.setIsMaster("isMaster");

        assertEquals("id", dto.getId());
        assertEquals("name", dto.getName());
        assertEquals("masterProject", dto.getMasterProject());
        assertEquals("isMaster", dto.getIsMaster());
    }
}
