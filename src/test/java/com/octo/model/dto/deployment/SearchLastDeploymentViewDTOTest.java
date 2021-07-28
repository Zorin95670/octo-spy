package com.octo.model.dto.deployment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;

public class SearchLastDeploymentViewDTOTest {

    @Test
    public void testGetterAndSetter() {
        SearchLastDeploymentViewDTO dto = new SearchLastDeploymentViewDTO();
        assertNull(dto.getOnMasterProject());

        dto.setAlive("onMasterProject");

        assertEquals("onMasterProject", dto.getOnMasterProject());
    }
}
