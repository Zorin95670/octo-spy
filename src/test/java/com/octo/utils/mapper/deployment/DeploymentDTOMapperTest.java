package com.octo.utils.mapper.deployment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.Instant;

import org.junit.Test;

import com.octo.model.dto.deployment.DeploymentDTO;
import com.octo.model.entity.Deployment;
import com.octo.model.entity.Environment;

public class DeploymentDTOMapperTest {

    @Test
    public void applyTest() {
        DeploymentDTOMapper mapper = new DeploymentDTOMapper();

        assertNull(mapper.apply(null));

        DeploymentDTO dto = mapper.apply(new Deployment());
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getEnvironment());
        assertNull(dto.getClient());
        assertFalse(dto.isAlive());
        assertNull(dto.getInsertDate());
        assertNull(dto.getUpdateDate());
        assertNull(dto.getVersion());

        Deployment entity = new Deployment();
        entity.setId(1L);
        Environment environment = new Environment();
        environment.setName("test");
        entity.setEnvironment(environment);
        entity.setClient("client");
        entity.setAlive(true);
        entity.setInsertDate(Timestamp.from(Instant.ofEpochMilli(1L)));
        entity.setUpdateDate(Timestamp.from(Instant.ofEpochMilli(2L)));
        entity.setVersion("version");

        dto = mapper.apply(entity);
        assertNotNull(dto);
        assertEquals(Long.valueOf(1L), dto.getId());
        assertEquals("test", dto.getEnvironment());
        assertEquals("client", dto.getClient());
        assertTrue(dto.isAlive());
        assertEquals(Timestamp.from(Instant.ofEpochMilli(1L)), entity.getInsertDate());
        assertEquals(Timestamp.from(Instant.ofEpochMilli(2L)), entity.getUpdateDate());
        assertEquals("version", dto.getVersion());
    }
}
