package com.octo.model.entity;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class InformationViewTest {

    @Test
    void testGetterAndSetter() {
        InformationView entity = new InformationView();

        assertNull(entity.getId());
        assertNull(entity.getVersion());

        entity.setId(1L);
        entity.setVersion("version");

        assertEquals(Long.valueOf(1L), entity.getId());
        assertEquals("version", entity.getVersion());
    }
}
