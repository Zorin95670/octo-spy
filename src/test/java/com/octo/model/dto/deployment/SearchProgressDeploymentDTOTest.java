package com.octo.model.dto.deployment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;

public class SearchProgressDeploymentDTOTest {

    @Test
    public void testGetterAndSetter() {
        SearchProgressDeploymentDTO dto = new SearchProgressDeploymentDTO();

        assertNull(dto.getId());
        assertNull(dto.getDeployment());

        dto.setId("id");
        dto.setDeployment("deployment");

        assertEquals("id", dto.getId());
        assertEquals("deployment", dto.getDeployment());
    }
}
