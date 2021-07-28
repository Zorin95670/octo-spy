package com.octo.model.dto.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.Timestamp;
import java.time.Instant;

import org.junit.jupiter.api.Test;

class ProjectDTOTest {

    @Test
    void testGetterAndSetter() {
        ProjectDTO dto = new ProjectDTO();

        assertNull(dto.getId());
        assertNull(dto.getInsertDate());
        assertNull(dto.getUpdateDate());
        assertNull(dto.getName());

        dto.setId(1L);
        dto.setInsertDate(Timestamp.from(Instant.ofEpochMilli(1L)));
        dto.setUpdateDate(Timestamp.from(Instant.ofEpochMilli(2L)));
        dto.setName("name");

        assertEquals(Long.valueOf(1L), dto.getId());
        assertEquals(Timestamp.from(Instant.ofEpochMilli(1L)), dto.getInsertDate());
        assertEquals(Timestamp.from(Instant.ofEpochMilli(2L)), dto.getUpdateDate());
        assertEquals("name", dto.getName());

        dto.setInsertDate(null);
        dto.setUpdateDate(null);
        assertNull(dto.getInsertDate());
        assertNull(dto.getUpdateDate());
    }

}
