package com.octo.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;

class GroupTest {

    @Test
    void testGetterAndSetter() {
        Group entity = new Group();

        assertNull(entity.getId());
        assertNull(entity.getMasterProject());

        entity.setId(1L);
        entity.setMasterProject(new Project());

        assertEquals(Long.valueOf(1L), entity.getId());
        assertNotNull(entity.getMasterProject());

        entity.prePersist();
        assertNotNull(entity.getInsertDate());
    }
}
