package com.octo.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;

class DeploymentProgressTest {

    @Test
    void testGetterAndSetter() {
        DeploymentProgress entity = new DeploymentProgress();

        assertNull(entity.getId());
        assertNull(entity.getDeployment());

        entity.setId(Long.valueOf(0L));
        entity.setDeployment(new Deployment());

        assertEquals(Long.valueOf(0L), entity.getId());
        assertNotNull(entity.getDeployment());

        entity.setInsertDate(null);
        entity.setUpdateDate(null);
    }

}
