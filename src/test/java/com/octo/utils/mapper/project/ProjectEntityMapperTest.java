package com.octo.utils.mapper.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.octo.model.dto.project.NewProjectDTO;
import com.octo.model.entity.Project;

public class ProjectEntityMapperTest {

    @Test
    public void applyTest() {
        ProjectEntityMapper mapper = new ProjectEntityMapper();

        assertNull(mapper.apply(null));

        Project entity = mapper.apply(new NewProjectDTO());
        assertNotNull(entity);
        assertNull(entity.getName());
        assertNull(entity.getId());
        assertNull(entity.getInsertDate());
        assertNull(entity.getUpdateDate());

        NewProjectDTO dto = new NewProjectDTO();
        dto.setName("test");
        entity = mapper.apply(dto);
        assertNotNull(entity);
        assertEquals("test", entity.getName());
        assertNull(entity.getId());
        assertNull(entity.getInsertDate());
        assertNull(entity.getUpdateDate());
    }
}
