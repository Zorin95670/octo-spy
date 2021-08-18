package com.octo.model.entity;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class AbstractProjectTest {

    class Entity extends AbstractProject {
    }

    @Test
    void testGetterAndSetter() {
        Entity entity = new Entity();

        assertNull(entity.getInsertDate());
        assertNull(entity.getColor());

        entity.prePersist();
        assertNotNull(entity.getInsertDate());
    }
}
