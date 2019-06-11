package com.octo.model.dto.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class NewProjectDTOTest {

    @Test
    public void testGetterAndSetter() {
        NewProjectDTO dto = new NewProjectDTO();

        assertNull(dto.getName());

        dto.setName("name");

        assertEquals("name", dto.getName());
    }
}
