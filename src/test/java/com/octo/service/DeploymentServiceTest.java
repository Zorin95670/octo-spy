package com.octo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.octo.dao.IDAO;
import com.octo.model.dto.deployment.DeploymentDTO;
import com.octo.model.dto.deployment.NewDeploymentDTO;
import com.octo.model.entity.Deployment;
import com.octo.model.entity.Environment;
import com.octo.model.entity.Project;
import com.octo.model.error.ErrorType;
import com.octo.model.exception.OctoException;

public class DeploymentServiceTest {

    @Mock
    IDAO<Environment> environmentDAO;

    @Mock
    IDAO<Deployment> deploymentDAO;

    @Mock
    IDAO<Project> projectDAO;

    @InjectMocks
    DeploymentService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadById() throws OctoException {
        // Test null ID
        OctoException exception = null;

        try {
            service.loadById(null);
        } catch (OctoException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.EMPTY_VALUE.getMessage(), exception.getError().getMessage());
        assertEquals("id", exception.getError().getField());
        assertNull(exception.getError().getValue());

        // Test unknow ID
        Mockito.when(this.deploymentDAO.loadById(Long.valueOf(1L))).thenReturn(null);
        exception = null;

        try {
            service.loadById(Long.valueOf(1L));
        } catch (OctoException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.ENTITY_NOT_FOUND.getMessage(), exception.getError().getMessage());
        assertEquals("id", exception.getError().getField());
        assertEquals(Long.valueOf(1L).toString(), exception.getError().getValue());

        // Test valid ID
        Mockito.when(this.deploymentDAO.loadById(Long.valueOf(2L))).thenReturn(new Deployment());
        exception = null;
        DeploymentDTO dto = null;
        try {
            dto = service.loadById(Long.valueOf(2L));
        } catch (OctoException e) {
            exception = e;
        }
        assertNull(exception);
        assertNotNull(dto);
    }

    @Test
    public void testSave() throws OctoException {
        // Test null environment
        OctoException exception = null;
        NewDeploymentDTO input = new NewDeploymentDTO();

        try {
            service.save(input);
        } catch (OctoException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.EMPTY_VALUE.getMessage(), exception.getError().getMessage());
        assertEquals("environment", exception.getError().getField());
        assertNull(exception.getError().getValue());

        // Test null client
        exception = null;
        input.setEnvironment("");

        try {
            service.save(input);
        } catch (OctoException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.EMPTY_VALUE.getMessage(), exception.getError().getMessage());
        assertEquals("project", exception.getError().getField());
        assertNull(exception.getError().getValue());

        // Test null version
        exception = null;
        input.setProject("");
        Mockito.when(this.projectDAO.loadById(Mockito.anyLong())).thenReturn(null);

        try {
            service.save(input);
        } catch (OctoException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.EMPTY_VALUE.getMessage(), exception.getError().getMessage());
        assertEquals("client", exception.getError().getField());
        assertNull(exception.getError().getValue());

        // Test null version
        exception = null;
        input.setClient("");

        try {
            service.save(input);
        } catch (OctoException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.EMPTY_VALUE.getMessage(), exception.getError().getMessage());
        assertEquals("version", exception.getError().getField());
        assertNull(exception.getError().getValue());

        // Test bad environment
        exception = null;
        input.setEnvironment("bad");
        input.setVersion("version");

        Mockito.when(this.environmentDAO.load(Mockito.any())).thenReturn(null);

        try {
            service.save(input);
        } catch (OctoException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.BAD_VALUE.getMessage(), exception.getError().getMessage());
        assertEquals("environment", exception.getError().getField());
        assertEquals("bad", exception.getError().getValue());

        // Test bad project
        exception = null;
        input.setEnvironment("environment");
        input.setVersion("version");
        input.setProject("bad");

        Mockito.when(this.environmentDAO.load(Mockito.any())).thenReturn(new Environment());
        Mockito.when(this.projectDAO.load(Mockito.any())).thenReturn(null);

        try {
            service.save(input);
        } catch (OctoException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.BAD_VALUE.getMessage(), exception.getError().getMessage());
        assertEquals("project", exception.getError().getField());
        assertEquals("bad", exception.getError().getValue());

        // Test all good
        exception = null;
        input.setClient("client");
        input.setEnvironment("QA");
        input.setVersion("version");
        input.setProject("project");

        Mockito.when(this.environmentDAO.load(Mockito.any())).thenReturn(new Environment());
        Mockito.when(this.deploymentDAO.save(Mockito.any())).thenReturn(new Deployment());
        Mockito.when(this.projectDAO.load(Mockito.any())).thenReturn(new Project());
        DeploymentDTO dto = null;

        try {
            dto = service.save(input);
        } catch (OctoException e) {
            exception = e;
        }

        assertNull(exception);
        assertNotNull(dto);

    }

}
