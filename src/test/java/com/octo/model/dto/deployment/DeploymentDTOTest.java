package com.octo.model.dto.deployment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.Instant;

import org.junit.jupiter.api.Test;

import com.octo.model.entity.Environment;
import com.octo.model.entity.Project;

public class DeploymentDTOTest {

    @Test
    public void testGetterAndSetter() {
        DeploymentDTO dto = new DeploymentDTO();

        assertFalse(dto.isAlive());
        assertNull(dto.getClient());
        assertNull(dto.getEnvironment());
        assertNull(dto.getProject());
        assertNull(dto.getMasterProject());
        assertNull(dto.getId());
        assertNull(dto.getInsertDate());
        assertNull(dto.getUpdateDate());
        assertNull(dto.getVersion());

        dto.setAlive(true);
        dto.setClient("client");
        dto.setEnvironment("environment");
        dto.setProject("project");
        dto.setMasterProject("master");
        dto.setId(1L);
        dto.setInsertDate(Timestamp.from(Instant.ofEpochMilli(1L)));
        dto.setUpdateDate(Timestamp.from(Instant.ofEpochMilli(2L)));
        dto.setVersion("version");

        assertTrue(dto.isAlive());
        assertEquals("client", dto.getClient());
        assertEquals(Long.valueOf(1L), dto.getId());
        assertEquals(Timestamp.from(Instant.ofEpochMilli(1L)), dto.getInsertDate());
        assertEquals(Timestamp.from(Instant.ofEpochMilli(2L)), dto.getUpdateDate());
        assertEquals("environment", dto.getEnvironment());
        assertEquals("project", dto.getProject());
        assertEquals("master", dto.getMasterProject());
        assertEquals("version", dto.getVersion());

        Environment environment = null;
        dto.setEnvironment(environment);
        assertNull(dto.getEnvironment());

        environment = new Environment();
        dto.setEnvironment(environment);
        assertNull(dto.getEnvironment());

        environment.setName("test");
        dto.setEnvironment(environment);
        assertEquals("test", dto.getEnvironment());

        Project project = null;
        dto.setProject(project);
        assertNull(dto.getProject());

        project = new Project();
        dto.setProject(project);
        assertNull(dto.getProject());

        project.setName("test");
        dto.setProject(project);
        assertEquals("test", dto.getProject());

        Project master = null;
        dto.setMasterProject(master);
        assertNull(dto.getMasterProject());

        master = new Project();
        dto.setMasterProject(master);
        assertNull(dto.getMasterProject());

        project.setName("master");
        dto.setMasterProject(project);
        assertEquals("master", dto.getMasterProject());

        dto.setInsertDate(null);
        dto.setUpdateDate(null);
        assertNull(dto.getInsertDate());
        assertNull(dto.getUpdateDate());
    }
}
