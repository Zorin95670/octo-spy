package com.octo.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Timestamp;
import java.time.Instant;

import org.junit.jupiter.api.Test;

class ProjectTest {

    @Test
    void testGetterAndSetter() {
        Project entity = new Project();

        assertNull(entity.getId());
        assertNull(entity.getName());
        assertNull(entity.getInsertDate());
        assertNull(entity.getUpdateDate());

        entity.setId(1L);
        entity.setInsertDate(Timestamp.from(Instant.ofEpochMilli(1L)));
        entity.setUpdateDate(Timestamp.from(Instant.ofEpochMilli(2L)));
        entity.setName("name");

        assertEquals(Long.valueOf(1L), entity.getId());
        assertEquals("name", entity.getName());
        assertEquals(Timestamp.from(Instant.ofEpochMilli(1L)), entity.getInsertDate());
        assertEquals(Timestamp.from(Instant.ofEpochMilli(2L)), entity.getUpdateDate());

        entity.setInsertDate(null);
        entity.setUpdateDate(null);
        assertNull(entity.getInsertDate());
        assertNull(entity.getUpdateDate());
    }

    @Test
    void testPrePersist() {
        Project entity = new Project();

        assertNull(entity.getId());
        assertNull(entity.getName());
        assertNull(entity.getInsertDate());
        assertNull(entity.getUpdateDate());

        entity.prePersist();

        assertNull(entity.getId());
        assertNull(entity.getName());
        assertNotNull(entity.getInsertDate());
        assertNull(entity.getUpdateDate());
    }

}
