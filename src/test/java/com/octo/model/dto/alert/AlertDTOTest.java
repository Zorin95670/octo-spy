package com.octo.model.dto.alert;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AlertDTOTest {

    @Test
    void testGetterAndSetter() {
        AlertDTO dto = new AlertDTO();

        assertNull(dto.getMessage());
        assertNull(dto.getSeverity());
        assertNull(dto.getType());

        dto.setMessage("message");
        dto.setSeverity("severity");
        dto.setType("type");

        assertEquals("message", dto.getMessage());
        assertEquals("severity", dto.getSeverity());
        assertEquals("type", dto.getType());
    }
}
