package com.octo.model.dto.deployment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;

import com.octo.model.entity.Environment;
import com.octo.model.entity.Project;

class SearchDeploymentViewDTOTest {

    @Test
    void testGetterAndSetter() {
        SearchDeploymentViewDTO dto = new SearchDeploymentViewDTO();
        assertNull(dto.getId());
        assertNull(dto.getEnvironment());
        assertNull(dto.getProject());
        assertNull(dto.getMasterProject());
        assertNull(dto.getClient());
        assertNull(dto.getVersion());
        assertNull(dto.getAlive());
        assertNull(dto.getInProgress());

        Environment environment = new Environment();
        environment.setName("environment");
        Project project = new Project();
        project.setName("project");
        Project master = new Project();
        master.setName("master");
        dto.setId("id");
        dto.setEnvironment(environment);
        dto.setProject(project);
        dto.setMasterProject(master);
        dto.setClient("client");
        dto.setVersion("version");
        dto.setAlive("alive");
        dto.setInProgress("progress");

        assertEquals("id", dto.getId());
        assertEquals("environment", dto.getEnvironment());
        assertEquals("project", dto.getProject());
        assertEquals("master", dto.getMasterProject());
        assertEquals("client", dto.getClient());
        assertEquals("version", dto.getVersion());
        assertEquals("alive", dto.getAlive());
        assertEquals("progress", dto.getInProgress());

        environment = null;
        project = null;
        dto.setEnvironment(environment);
        dto.setProject(project);
        dto.setMasterProject(project);

        assertNull(dto.getEnvironment());
        assertNull(dto.getProject());
        assertNull(dto.getMasterProject());
    }
}
