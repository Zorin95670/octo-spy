package com.octo.model.dto.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class NewProjectDTOTest {

    @Test
    public void testGetterAndSetter() {
        NewProjectDTO dto = new NewProjectDTO();

        assertNull(dto.getName());
        assertNull(dto.getMasterName());
        assertFalse(dto.getIsMaster());

        dto.setName("name");
        dto.setMasterName("test");
        dto.setIsMaster(true);

        assertEquals("name", dto.getName());
        assertEquals("test", dto.getMasterName());
        assertTrue(dto.getIsMaster());
    }
}
