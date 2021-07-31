package com.octo.model.entity;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

class IPrePersistanceTest {

    class Entity implements IPrePersistance {
        public Timestamp date;

        @Override
        public void setInsertDate(Timestamp insertDate) {
            date = insertDate;
        }
    }

    @Test
    void testPrePersist() {
        Entity entity = new Entity();

        assertNull(entity.date);

        entity.prePersist();
        assertNotNull(entity.date);
    }
}
