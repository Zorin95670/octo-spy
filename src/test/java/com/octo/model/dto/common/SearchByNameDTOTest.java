package com.octo.model.dto.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;

class SearchByNameDTOTest {

    @Test
    void testGetterAndSetter() {
        SearchByNameDTO dto = new SearchByNameDTO(null);
        assertNull(dto.getName());

        dto = new SearchByNameDTO("test");
        assertEquals("test", dto.getName());
    }
}
