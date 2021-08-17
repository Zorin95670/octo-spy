package com.octo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.octo.dao.IDAO;
import com.octo.model.common.Resource;
import com.octo.model.dto.deployment.DeploymentDTO;
import com.octo.model.dto.deployment.NewDeploymentRecord;
import com.octo.model.dto.deployment.SearchDeploymentDTO;
import com.octo.model.dto.deployment.SearchDeploymentViewDTO;
import com.octo.model.entity.Deployment;
import com.octo.model.entity.DeploymentProgress;
import com.octo.model.entity.DeploymentView;
import com.octo.model.entity.Environment;
import com.octo.model.entity.Project;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.utils.Configuration;
import com.octo.utils.predicate.filter.QueryFilter;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
class DeploymentServiceTest {

    @Mock
    IDAO<Environment, QueryFilter> environmentDAO;

    @Mock
    IDAO<Deployment, QueryFilter> deploymentDAO;

    @Mock
    IDAO<DeploymentView, QueryFilter> deploymentViewDAO;

    @Mock
    IDAO<DeploymentProgress, QueryFilter> deploymentProgressDAO;

    @Mock
    IDAO<Project, QueryFilter> projectDAO;

    @Mock
    Configuration configuration;

    @InjectMocks
    DeploymentService service;

    @Test
    void testLoad() {
        Mockito.when(this.deploymentDAO.loadEntityById(Mockito.any())).thenReturn(new Deployment());
        assertNotNull(service.load(1L));
    }

    @Test
    void testSave() {
        // Test null environment
        GlobalException exception = null;
        NewDeploymentRecord input = new NewDeploymentRecord(null, null, null, null, false, false);
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
        input = new NewDeploymentRecord("", null, null, null, false, false);

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
        input = new NewDeploymentRecord("", "", null, null, false, false);

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
        input = new NewDeploymentRecord("", "", null, "", false, false);

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
        input = new NewDeploymentRecord("QA", "project", "version", "client", false, false);

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
        input = new NewDeploymentRecord("QA", "project", "version", "client", true, false);

        Mockito.when(this.environmentDAO.load(Mockito.any())).thenReturn(Optional.of(environment));
        Mockito.when(this.projectDAO.load(Mockito.any())).thenReturn(Optional.of(project));
        Mockito.when(this.deploymentDAO.save(Mockito.any())).thenReturn(new Deployment());
        dto = null;

        try {
            dto = service.save(input);
        } catch (GlobalException e) {
            exception = e;
        }

        assertNull(exception);
        assertNotNull(dto);

        input = new NewDeploymentRecord("QA", "project", "version", "client", false, false);
        try {
            dto = service.save(input);
        } catch (GlobalException e) {
            exception = e;
        }

        assertNull(exception);
        assertNotNull(dto);
    }

    @Test
    void testSaveWithProgress() {
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
        Mockito.when(this.deploymentDAO.save(Mockito.any())).thenReturn(deployment);
        Mockito.when(this.deploymentProgressDAO.save(Mockito.any())).thenReturn(null);
        NewDeploymentRecord dto = new NewDeploymentRecord("QA", "project", "version", "client", true, true);

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
    void testDisablePreviousDeployment() {
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
    void testDelete() {
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
    void testDeleteProgress() {
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
    void testDeleteProgressWithData() {
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

    @Test
    void testCount() {
        Mockito.when(this.deploymentViewDAO.count(Mockito.any())).thenReturn(1L);
        assertEquals(Long.valueOf(1L), this.service.count(null));
    }

    @Test
    void testFind() {
        List<DeploymentView> list = new ArrayList<DeploymentView>();
        Mockito.when(this.deploymentViewDAO.count(Mockito.any())).thenReturn(2L);
        Mockito.when(this.deploymentViewDAO.find(Mockito.any())).thenReturn(list);

        Mockito.when(this.configuration.getDefaultApiLimit()).thenReturn(1);
        Mockito.when(this.configuration.getMaximumApiLimit()).thenReturn(10);

        SearchDeploymentViewDTO dto = new SearchDeploymentViewDTO();
        dto.setPage(-1);

        GlobalException exception = null;

        try {
            this.service.find(dto);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.WRONG_VALUE.getMessage(), exception.getError().getMessage());
        assertEquals("page", exception.getError().getField());
        exception = null;

        dto.setPage(0);
        Resource<DeploymentDTO> deployments = null;
        try {
            deployments = this.service.find(dto);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
        assertNotNull(deployments);
        assertEquals(0, deployments.getPage());
        assertEquals(1, deployments.getCount());
        assertEquals(Long.valueOf(2L), deployments.getTotal());
        assertEquals(new ArrayList<>(), deployments.getResources());

        dto.setCount(100);
        deployments = null;
        try {
            deployments = this.service.find(dto);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
        assertNotNull(deployments);
        assertEquals(0, deployments.getPage());
        assertEquals(10, deployments.getCount());
        assertEquals(Long.valueOf(2L), deployments.getTotal());
        assertEquals(new ArrayList<>(), deployments.getResources());

        dto.setCount(9);
        deployments = null;
        try {
            deployments = this.service.find(dto);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
        assertNotNull(deployments);
        assertEquals(0, deployments.getPage());
        assertEquals(9, deployments.getCount());
        assertEquals(Long.valueOf(2L), deployments.getTotal());
        assertEquals(new ArrayList<>(), deployments.getResources());
    }
}
