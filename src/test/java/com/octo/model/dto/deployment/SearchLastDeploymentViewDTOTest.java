package com.octo.model.dto.deployment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;

class SearchLastDeploymentViewDTOTest {

    @Test
    void testGetterAndSetter() {
        SearchLastDeploymentViewDTO dto = new SearchLastDeploymentViewDTO();
        assertNull(dto.getOnMasterProject());

        dto.setOnMasterProject("onMasterProject");

        assertEquals("onMasterProject", dto.getOnMasterProject());
    }
}
