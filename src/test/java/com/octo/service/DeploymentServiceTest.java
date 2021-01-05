package com.octo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cji.dao.IDAO;
import com.cji.models.error.ErrorType;
import com.cji.models.error.GlobalException;
import com.cji.utils.predicate.filter.QueryFilter;
import com.octo.model.dto.deployment.DeploymentDTO;
import com.octo.model.dto.deployment.NewDeploymentDTO;
import com.octo.model.dto.deployment.SearchDeploymentDTO;
import com.octo.model.entity.Deployment;
import com.octo.model.entity.DeploymentProgress;
import com.octo.model.entity.Environment;
import com.octo.model.entity.Project;

public class DeploymentServiceTest {

    @Mock
    IDAO<Environment, QueryFilter> environmentDAO;

    @Mock
    IDAO<Deployment, QueryFilter> deploymentDAO;

    @Mock
    IDAO<DeploymentProgress, QueryFilter> deploymentProgressDAO;

    @Mock
    IDAO<Project, QueryFilter> projectDAO;

    @InjectMocks
    DeploymentService service;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoad() {
        Mockito.when(this.deploymentDAO.loadEntityById(Mockito.any())).thenReturn(new Deployment());
        assertNotNull(service.load(1L));
    }

    @Test
    public void testSave() {
        // Test null environment
        GlobalException exception = null;
        NewDeploymentDTO input = new NewDeploymentDTO();
        Mockito.when(this.environmentDAO.load(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(this.projectDAO.load(Mockito.any())).thenReturn(Optional.empty());

        try {
            service.save(input);
        } catch (GlobalException e) {
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
        } catch (GlobalException e) {
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
        } catch (GlobalException e) {
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
        } catch (GlobalException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.EMPTY_VALUE.getMessage(), exception.getError().getMessage());
        assertEquals("version", exception.getError().getField());
        assertNull(exception.getError().getValue());

        // Test all good
        exception = null;
        input.setClient("client");
        input.setEnvironment("QA");
        input.setVersion("version");
        input.setProject("project");

        Environment environment = new Environment();
        environment.setId(1L);
        environment.setName("QA");
        Project project = new Project();
        project.setId(1L);
        project.setName("project");
        Deployment deployment = new Deployment();
        deployment.setAlive(true);
        deployment.setClient("client");
        deployment.setEnvironment(environment);
        deployment.setProject(project);
        Mockito.when(this.deploymentDAO.save(Mockito.any())).thenReturn(deployment);
        DeploymentDTO dto = null;

        try {
            dto = service.save(input);
        } catch (GlobalException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.ENTITY_NOT_FOUND.getMessage(), exception.getError().getMessage());
        assertEquals("environment", exception.getError().getField());
        Mockito.when(this.environmentDAO.load(Mockito.any())).thenReturn(Optional.of(environment));

        try {
            dto = service.save(input);
        } catch (GlobalException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.ENTITY_NOT_FOUND.getMessage(), exception.getError().getMessage());
        assertEquals("project", exception.getError().getField());

        // Test all good and disable previous deployment
        exception = null;
        input.setClient("client");
        input.setEnvironment("QA");
        input.setVersion("version");
        input.setProject("project");
        input.setAlive(true);

        Mockito.when(this.environmentDAO.load(Mockito.any())).thenReturn(Optional.of(environment));
        Mockito.when(this.projectDAO.load(Mockito.any())).thenReturn(Optional.of(project));
        Mockito.when(this.deploymentDAO.save(Mockito.any())).thenReturn(new Deployment());
        Mockito.when(this.deploymentDAO.loadById(Mockito.any())).thenReturn(null);
        dto = null;

        try {
            dto = service.save(input);
        } catch (GlobalException e) {
            exception = e;
        }

        assertNull(exception);
        assertNotNull(dto);

        input.setAlive(false);
        try {
            dto = service.save(input);
        } catch (GlobalException e) {
            exception = e;
        }

        assertNull(exception);
        assertNotNull(dto);
    }

    @Test
    public void testSaveWithProgress() {
        Environment environment = new Environment();
        environment.setId(1L);
        environment.setName("QA");
        Project project = new Project();
        project.setId(1L);
        project.setName("project");
        Deployment deployment = new Deployment();
        deployment.setAlive(true);
        deployment.setClient("client");
        deployment.setEnvironment(environment);
        deployment.setProject(project);

        Mockito.when(this.environmentDAO.load(Mockito.any())).thenReturn(Optional.of(environment));
        Mockito.when(this.projectDAO.load(Mockito.any())).thenReturn(Optional.of(project));
        Mockito.when(this.deploymentDAO.loadById(Mockito.any())).thenReturn(deployment);
        Mockito.when(this.deploymentDAO.save(Mockito.any())).thenReturn(deployment);
        Mockito.when(this.deploymentProgressDAO.save(Mockito.any())).thenReturn(null);
        NewDeploymentDTO dto = new NewDeploymentDTO();
        dto.setClient("client");
        dto.setEnvironment("QA");
        dto.setVersion("version");
        dto.setProject("project");
        dto.setAlive(true);
        dto.setInProgress(true);

        GlobalException exception = null;
        try {
            service.save(dto);
        } catch (GlobalException e) {
            exception = e;
        }

        assertNull(exception);
        Mockito.verify(this.deploymentProgressDAO, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void testDisablePreviousDeployment() {
        Project project = new Project();
        project.setId(1L);

        Environment environment = new Environment();
        environment.setId(1L);

        Deployment deployment = new Deployment();
        deployment.setProject(project);
        deployment.setEnvironment(environment);

        // Test no entity to disable
        GlobalException exception = null;
        try {
            Mockito.when(this.deploymentDAO.find(Mockito.any())).thenReturn(new ArrayList<>());
            this.service.disablePreviousDeployment(deployment);
        } catch (GlobalException e) {
            exception = e;
        }

        // Test entity to disable
        Deployment entityToUpdate = new Deployment();
        entityToUpdate.setAlive(false);
        assertNull(exception);
        exception = null;
        try {
            List<Deployment> list = new ArrayList<>();
            list.add(entityToUpdate);
            Mockito.when(this.deploymentDAO.find(Mockito.any())).thenReturn(list);
            Mockito.when(this.deploymentDAO.save(Mockito.any())).thenReturn(null);
            this.service.disablePreviousDeployment(deployment);
        } catch (GlobalException e) {
            exception = e;
        }

        assertNull(exception);
        assertFalse(entityToUpdate.isAlive());
    }

    @Test
    public void testDelete() {
        Mockito.when(this.deploymentDAO.loadById(Mockito.any())).thenReturn(new Deployment());
        Mockito.doNothing().when(this.deploymentDAO).delete(Mockito.any());

        GlobalException exception = null;
        try {
            this.service.delete(1L);
        } catch (GlobalException e) {
            exception = e;
        }

        assertNull(exception);
    }

    @Test
    public void testDeleteProgress() {
        Project project = new Project();
        project.setId(1L);

        Environment environment = new Environment();
        environment.setId(1L);

        Deployment deployment = new Deployment();
        deployment.setId(1L);
        deployment.setProject(project);
        deployment.setEnvironment(environment);

        SearchDeploymentDTO dto = new SearchDeploymentDTO();
        GlobalException exception = null;
        try {
            this.service.deleteProgressDeployment(dto);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.EMPTY_VALUE.getMessage(), exception.getError().getMessage());
        assertEquals("environment", exception.getError().getField());

        Mockito.when(this.environmentDAO.load(Mockito.any())).thenReturn(Optional.empty());
        dto.setEnvironment("test");
        exception = null;
        try {
            this.service.deleteProgressDeployment(dto);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.ENTITY_NOT_FOUND.getMessage(), exception.getError().getMessage());
        assertEquals("environment", exception.getError().getField());

        Mockito.when(this.environmentDAO.load(Mockito.any())).thenReturn(Optional.of(environment));
        exception = null;
        try {
            this.service.deleteProgressDeployment(dto);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.EMPTY_VALUE.getMessage(), exception.getError().getMessage());
        assertEquals("project", exception.getError().getField());

        Mockito.when(this.projectDAO.load(Mockito.any())).thenReturn(Optional.empty());
        dto.setProject("test");
        exception = null;
        try {
            this.service.deleteProgressDeployment(dto);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.ENTITY_NOT_FOUND.getMessage(), exception.getError().getMessage());
        assertEquals("project", exception.getError().getField());

        Mockito.when(this.projectDAO.load(Mockito.any())).thenReturn(Optional.of(project));
        Mockito.when(this.deploymentDAO.load(Mockito.any())).thenReturn(Optional.empty());
        exception = null;
        try {
            this.service.deleteProgressDeployment(dto);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.ENTITY_NOT_FOUND.getMessage(), exception.getError().getMessage());
        assertEquals("deployment", exception.getError().getField());

        Mockito.when(this.deploymentDAO.load(Mockito.any())).thenReturn(Optional.of(deployment));
        Mockito.when(this.deploymentProgressDAO.load(Mockito.any())).thenReturn(Optional.empty());
        exception = null;
        try {
            this.service.deleteProgressDeployment(dto);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.ENTITY_NOT_FOUND.getMessage(), exception.getError().getMessage());
        assertEquals("deploymentProgress", exception.getError().getField());
    }

    @Test
    public void testDeleteProgressWithData() {
        Project project = new Project();
        project.setId(1L);

        Environment environment = new Environment();
        environment.setId(1L);

        Deployment deployment = new Deployment();
        deployment.setId(1L);
        deployment.setProject(project);
        deployment.setEnvironment(environment);

        GlobalException exception = null;
        SearchDeploymentDTO dto = new SearchDeploymentDTO();
        dto.setEnvironment("test");
        dto.setProject("test");
        Mockito.when(this.deploymentDAO.load(Mockito.any())).thenReturn(Optional.of(deployment));
        Mockito.when(this.deploymentProgressDAO.load(Mockito.any())).thenReturn(Optional.of(new DeploymentProgress()));
        Mockito.when(this.environmentDAO.load(Mockito.any())).thenReturn(Optional.of(environment));
        Mockito.when(this.projectDAO.load(Mockito.any())).thenReturn(Optional.of(project));
        exception = null;
        try {
            this.service.deleteProgressDeployment(dto);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
    }
}
