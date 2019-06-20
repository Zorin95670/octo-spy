package com.octo.utils.mapper.deployment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.octo.model.dto.deployment.NewDeploymentDTO;
import com.octo.model.entity.Deployment;

public class DeploymentEntityMapperTest {

    @Test
    public void applyTest() {
        DeploymentEntityMapper mapper = new DeploymentEntityMapper();

        assertNull(mapper.apply(null));

        Deployment entity = mapper.apply(new NewDeploymentDTO());
        assertNotNull(entity);
        assertFalse(entity.isAlive());
        assertNull(entity.getClient());
        assertNull(entity.getVersion());

        NewDeploymentDTO dto = new NewDeploymentDTO();
        dto.setAlive(true);
        dto.setClient("client");
        dto.setEnvironment("test");
        dto.setVersion("version");
        entity = mapper.apply(dto);
        assertNotNull(entity);
        assertTrue(entity.isAlive());
        assertNull(entity.getEnvironment());
        assertEquals("client", entity.getClient());
        assertEquals("version", entity.getVersion());
        assertNull(entity.getId());
        assertNull(entity.getInsertDate());
        assertNull(entity.getUpdateDate());
    }
}
