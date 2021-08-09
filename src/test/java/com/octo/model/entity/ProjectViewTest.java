package com.octo.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;

class ProjectViewTest {

    @Test
    void testGetterAndSetter() {
        ProjectView entity = new ProjectView();

        assertNull(entity.getMasterProject());
        assertNull(entity.getMasterProjectColor());

        entity.setId(1L);
        entity.setMasterProject("masterProject");
        entity.setMasterProjectColor("masterProjectColor");

        assertEquals("masterProject", entity.getMasterProject());
        assertEquals("masterProjectColor", entity.getMasterProjectColor());
    }
}
