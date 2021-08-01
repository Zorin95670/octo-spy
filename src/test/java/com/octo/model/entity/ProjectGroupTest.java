package com.octo.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;

class ProjectGroupTest {

    @Test
    void testGetterAndSetter() {
        ProjectGroup entity = new ProjectGroup();

        assertNull(entity.getId());
        assertNull(entity.getGroup());
        assertNull(entity.getProject());

        entity.setId(1L);
        entity.setGroup(new Group());
        entity.setProject(new Project());

        assertEquals(Long.valueOf(1L), entity.getId());
        assertNotNull(entity.getGroup());
        assertNotNull(entity.getProject());

        entity.prePersist();
        assertNotNull(entity.getInsertDate());
    }
}
