package com.octo.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

class ProjectViewTest {

    @Test
    void testGetterAndSetter() {
        ProjectView entity = new ProjectView();

        assertNull(entity.getMasterProject());
        assertNull(entity.getMasterProjectColor());
        assertFalse(entity.getIsMaster());

        entity.setId(1L);
        entity.setMasterProject("masterProject");
        entity.setMasterProjectColor("masterProjectColor");
        entity.setIsMaster(true);

        assertEquals("masterProject", entity.getMasterProject());
        assertEquals("masterProjectColor", entity.getMasterProjectColor());
        assertTrue(entity.getIsMaster());
    }
}
