package com.octo.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Timestamp;
import java.time.Instant;

import org.junit.jupiter.api.Test;

public class GroupTest {

    @Test
    public void testGetterAndSetter() {
        Group entity = new Group();

        assertNull(entity.getId());
        assertNull(entity.getMasterProject());
        assertNull(entity.getInsertDate());
        assertNull(entity.getUpdateDate());

        entity.setId(1L);
        entity.setMasterProject(new Project());
        entity.setInsertDate(Timestamp.from(Instant.ofEpochMilli(2L)));
        entity.setUpdateDate(Timestamp.from(Instant.ofEpochMilli(3L)));

        assertEquals(Long.valueOf(1L), entity.getId());
        assertNotNull(entity.getMasterProject());
        assertEquals(Timestamp.from(Instant.ofEpochMilli(2L)), entity.getInsertDate());
        assertEquals(Timestamp.from(Instant.ofEpochMilli(3L)), entity.getUpdateDate());

        entity.setInsertDate(null);
        entity.setUpdateDate(null);
        assertNull(entity.getInsertDate());
        assertNull(entity.getUpdateDate());

        entity.prePersist();
        assertNotNull(entity.getInsertDate());
    }
}
