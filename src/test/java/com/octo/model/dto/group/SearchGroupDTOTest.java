package com.octo.model.dto.group;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SearchGroupDTOTest {

    @Test
    void testGetterAndSetter() {
        SearchGroupDTO dto = new SearchGroupDTO();

        assertNull(dto.getId());
        assertNull(dto.getMasterProject());

        dto.setId("id");
        dto.setMasterProject("masterProject");

        assertEquals("id", dto.getId());
        assertEquals("masterProject", dto.getMasterProject());
    }
}
