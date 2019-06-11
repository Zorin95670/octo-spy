package com.octo.utils.mapper.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Timestamp;
import java.time.Instant;

import org.junit.Test;

import com.octo.model.dto.project.ProjectDTO;
import com.octo.model.entity.Project;

public class ProjectDTOMapperTest {

    @Test
    public void applyTest() {
        ProjectDTOMapper mapper = new ProjectDTOMapper();

        assertNull(mapper.apply(null));

        ProjectDTO dto = mapper.apply(new Project());
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getInsertDate());
        assertNull(dto.getUpdateDate());

        Project entity = new Project();
        entity.setId(1L);
        entity.setName("test");
        entity.setInsertDate(Timestamp.from(Instant.ofEpochMilli(1L)));
        entity.setUpdateDate(Timestamp.from(Instant.ofEpochMilli(2L)));

        dto = mapper.apply(entity);
        assertNotNull(dto);
        assertEquals(Long.valueOf(1L), dto.getId());
        assertEquals("test", dto.getName());
        assertEquals(Timestamp.from(Instant.ofEpochMilli(1L)), entity.getInsertDate());
        assertEquals(Timestamp.from(Instant.ofEpochMilli(2L)), entity.getUpdateDate());
    }
}
