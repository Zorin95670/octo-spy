package com.octo.model.dto.environment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;

class EnvironmentDTOTest {

    @Test
    void testGetterAndSetter() {
        EnvironmentDTO dto = new EnvironmentDTO();

        assertNull(dto.getId());
        assertNull(dto.getName());
        assertEquals(0, dto.getPosition());

        dto.setId(1L);
        dto.setName("name");
        dto.setPosition(1);

        assertEquals(Long.valueOf(1L), dto.getId());
        assertEquals("name", dto.getName());
        assertEquals(1, dto.getPosition());

        dto = new EnvironmentDTO(2L, "test");

        assertEquals(Long.valueOf(2L), dto.getId());
        assertEquals("test", dto.getName());
    }
}
