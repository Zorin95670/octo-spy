package com.octo.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;

class DeploymentReportTest {

    @Test
    void testGetterAndSetter() {
        DeploymentReportView entity = new DeploymentReportView();

        assertNull(entity.getId());
        assertNull(entity.getEnvironment());
        assertNull(entity.getProject());
        assertNull(entity.getMasterProject());
        assertNull(entity.getClient());
        assertEquals(0, entity.getYear());
        assertEquals(0, entity.getMonth());
        assertEquals(0, entity.getDayOfWeek());
        assertEquals(0, entity.getDay());
        assertEquals(0, entity.getHour());

        entity.setClient("client");
        entity.setId(1L);
        entity.setEnvironment(2l);
        entity.setProject(3L);
        entity.setMasterProject(4L);
        entity.setYear(5);
        entity.setMonth(6);
        entity.setDayOfWeek(7);
        entity.setDay(8);
        entity.setHour(9);

        assertEquals(Long.valueOf(1L), entity.getId());
        assertEquals(Long.valueOf(2L), entity.getEnvironment());
        assertEquals(Long.valueOf(3L), entity.getProject());
        assertEquals(Long.valueOf(4L), entity.getMasterProject());
        assertEquals(5, entity.getYear());
        assertEquals(6, entity.getMonth());
        assertEquals(7, entity.getDayOfWeek());
        assertEquals(8, entity.getDay());
        assertEquals(9, entity.getHour());
    }
}
