package com.octo.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.Timestamp;
import java.time.Instant;

import org.junit.jupiter.api.Test;

class ProjectViewTest {

    @Test
    void testGetterAndSetter() {
        ProjectView entity = new ProjectView();

        assertNull(entity.getMasterProject());

        entity.setId(1L);
        entity.setInsertDate(Timestamp.from(Instant.ofEpochMilli(1L)));
        entity.setUpdateDate(Timestamp.from(Instant.ofEpochMilli(2L)));
        entity.setMasterProject("masterProject");

        assertEquals("masterProject", entity.getMasterProject());
    }
}
