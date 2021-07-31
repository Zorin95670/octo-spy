package com.octo.model.entity;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.time.Instant;

import org.junit.jupiter.api.Test;

class AbstractEntityTest {

    class Entity extends AbstractEntity {
    }

    @Test
    public void testGetterAndSetter() {
        Entity entity = new Entity();

        assertNull(entity.getInsertDate());
        assertNull(entity.getUpdateDate());

        entity.setInsertDate(Timestamp.from(Instant.ofEpochMilli(1L)));
        entity.setUpdateDate(Timestamp.from(Instant.ofEpochMilli(2L)));
        assertEquals(Timestamp.from(Instant.ofEpochMilli(1L)), entity.getInsertDate());
        assertEquals(Timestamp.from(Instant.ofEpochMilli(2L)), entity.getUpdateDate());

        entity.setInsertDate(null);
        entity.setUpdateDate(null);
        assertNull(entity.getInsertDate());
        assertNull(entity.getUpdateDate());
    }
}
