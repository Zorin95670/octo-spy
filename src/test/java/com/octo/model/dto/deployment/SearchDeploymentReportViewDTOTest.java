package com.octo.model.dto.deployment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;

class SearchDeploymentReportViewDTOTest {

    @Test
    void testGetterAndSetter() {
        SearchDeploymentReportViewDTO dto = new SearchDeploymentReportViewDTO();
        assertNull(dto.getId());
        assertNull(dto.getEnvironment());
        assertNull(dto.getProject());
        assertNull(dto.getMasterProject());
        assertNull(dto.getClient());
        assertNull(dto.getYear());
        assertNull(dto.getMonth());
        assertNull(dto.getDayOfWeek());
        assertNull(dto.getDay());
        assertNull(dto.getHour());

        dto.setId("id");
        dto.setClient("client");
        dto.setEnvironment("environment");
        dto.setProject("project");
        dto.setMasterProject("master");
        dto.setYear("year");
        dto.setMonth("month");
        dto.setDayOfWeek("dayOfWeek");
        dto.setDay("day");
        dto.setHour("hour");

        assertEquals("id", dto.getId());
        assertEquals("environment", dto.getEnvironment());
        assertEquals("project", dto.getProject());
        assertEquals("master", dto.getMasterProject());
        assertEquals("client", dto.getClient());
        assertEquals("year", dto.getYear());
        assertEquals("month", dto.getMonth());
        assertEquals("dayOfWeek", dto.getDayOfWeek());
        assertEquals("day", dto.getDay());
        assertEquals("hour", dto.getHour());
    }
}
