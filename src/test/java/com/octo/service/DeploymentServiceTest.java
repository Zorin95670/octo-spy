package com.octo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.octo.controller.model.QueryFilter;
import com.octo.model.deployment.DeploymentRecord;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.persistence.model.Deployment;
import com.octo.persistence.model.DeploymentProgress;
import com.octo.persistence.model.DeploymentView;
import com.octo.persistence.model.Environment;
import com.octo.persistence.model.Project;
import com.octo.persistence.repository.DeploymentProgressRepository;
import com.octo.persistence.repository.DeploymentRepository;
import com.octo.persistence.repository.DeploymentViewRepository;
import com.octo.persistence.repository.EnvironmentRepository;
import com.octo.persistence.repository.ProjectRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@Tag("unit")
class DeploymentServiceTest {

    @Mock
    DeploymentRepository deploymentRepository;

    @Mock
    DeploymentViewRepository deploymentViewRepository;

    @Mock
    DeploymentProgressRepository deploymentProgressRepository;

    @Mock
    EnvironmentRepository environmentRepository;

    @Mock
    ProjectRepository projectRepository;

    @InjectMocks
    DeploymentServiceImpl service;

    @Test
    void testLoad() {
        Mockito.when(this.deploymentRepository.findById(Mockito.any())).thenReturn(Optional.of(new Deployment()));
        assertNotNull(service.load(1L));
    }

    @Test
    void testLoadView() {
        Mockito.when(this.deploymentViewRepository.findById(Mockito.any())).thenReturn(Optional.of(new DeploymentView()));
        assertNotNull(service.loadView(1L));
    }

    @Test
    void testFind() {
        Mockito.when(this.deploymentViewRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));
        assertNotNull(service.find(Map.of(), new QueryFilter().getPagination()));
    }

    @Test
    void testSave() {
        // Test null environment
        GlobalException exception = null;
        DeploymentRecord input = new DeploymentRecord(null, null, null, null, false, false);
        Mockito.when(this.environmentRepository.findByName(Mockito.any())).thenReturn(Optional.empty());

        try {
            service.save(input);
        } catch (GlobalException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.WRONG_VALUE.getMessage(), exception.getError().getMessage());
        assertEquals("environment", exception.getError().getField());
        assertNull(exception.getError().getValue());

        // Test null project
        Mockito.when(this.environmentRepository.findByName(Mockito.any())).thenReturn(Optional.of(new Environment()));
        Mockito.when(this.projectRepository.findByName(Mockito.any())).thenReturn(Optional.empty());
        exception = null;
        input = new DeploymentRecord(null, null, null, null, false, false);

        try {
            service.save(input);
        } catch (GlobalException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.WRONG_VALUE.getMessage(), exception.getError().getMessage());
        assertEquals("project", exception.getError().getField());
        assertNull(exception.getError().getValue());

        // Test all good
        Environment environment = new Environment();
        environment.setId(1L);
        Project project = new Project();
        project.setId(1L);
        Deployment deployment = new Deployment();
        deployment.setId(1L);
        Mockito.when(this.environmentRepository.findByName(Mockito.any())).thenReturn(Optional.of(environment));
        Mockito.when(this.projectRepository.findByName(Mockito.any())).thenReturn(Optional.of(project));
        Mockito.when(this.deploymentRepository.save(Mockito.any())).thenReturn(deployment);

        // Test all good and disable previous deployment
        exception = null;
        input = new DeploymentRecord("QA", "project", "version", "client", true, false);
        Mockito.when(this.deploymentViewRepository.getById(Mockito.any())).thenReturn(new DeploymentView());
        DeploymentView dto = null;

        try {
            dto = service.save(input);
        } catch (GlobalException e) {
            exception = e;
        }

        assertNull(exception);
        assertNotNull(dto);

        input = new DeploymentRecord("QA", "project", "version", "client", false, false);
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

        Mockito.doNothing().when(this.deploymentProgressRepository).deleteAllProgress(Mockito.any(), Mockito.any(),
                Mockito.any());
        Mockito.when(this.environmentRepository.findByName(Mockito.any())).thenReturn(Optional.of(environment));
        Mockito.when(this.projectRepository.findByName(Mockito.any())).thenReturn(Optional.of(project));
        Mockito.when(this.deploymentRepository.save(Mockito.any())).thenReturn(deployment);
        Mockito.when(this.deploymentProgressRepository.save(Mockito.any())).thenReturn(null);
        DeploymentRecord dto = new DeploymentRecord("QA", "project", "version", "client", true, true);

        GlobalException exception = null;
        try {
            service.save(dto);
        } catch (GlobalException e) {
            exception = e;
        }

        assertNull(exception);
        Mockito.verify(this.deploymentProgressRepository, Mockito.times(1)).save(Mockito.any());
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
            Mockito.when(this.deploymentRepository.findOne(Mockito.any(Specification.class)))
                    .thenReturn(Optional.empty());
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
            Mockito.when(this.deploymentRepository.findOne(Mockito.any(Specification.class)))
                    .thenReturn(Optional.of(entityToUpdate));
            Mockito.when(this.deploymentRepository.save(Mockito.any())).thenReturn(null);
            this.service.disablePreviousDeployment(deployment);
        } catch (GlobalException e) {
            exception = e;
        }

        assertNull(exception);
        assertFalse(entityToUpdate.isAlive());
    }

    @Test
    void testDelete() {
        Mockito.when(this.deploymentRepository.findById(Mockito.any())).thenReturn(Optional.of(new Deployment()));
        Mockito.doNothing().when(this.deploymentRepository).deleteById(Mockito.any());

        GlobalException exception = null;
        try {
            this.service.delete(1L);
        } catch (GlobalException e) {
            exception = e;
        }

        assertNull(exception);
    }

    @Test
    void testDeleteProgressEmptyEnvironment() {
        GlobalException exception = null;
        try {
            this.service.deleteProgressDeployment(Map.of());
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.EMPTY_VALUE.getMessage(), exception.getError().getMessage());
        assertEquals("environment", exception.getError().getField());

        exception = null;
        try {
            this.service.deleteProgressDeployment(Map.of("environment", ""));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.EMPTY_VALUE.getMessage(), exception.getError().getMessage());
        assertEquals("environment", exception.getError().getField());
    }

    @Test
    void testDeleteProgressEmptyProject() {
        GlobalException exception = null;
        try {
            this.service.deleteProgressDeployment(Map.of("environment", "test"));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.EMPTY_VALUE.getMessage(), exception.getError().getMessage());
        assertEquals("project", exception.getError().getField());

        exception = null;
        try {
            this.service.deleteProgressDeployment(Map.of("environment", "test", "project", ""));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.EMPTY_VALUE.getMessage(), exception.getError().getMessage());
        assertEquals("project", exception.getError().getField());
    }

    @Test
    void testDeleteProgressEmptyDeployment() {
        DeploymentView deployment = new DeploymentView();
        deployment.setId(1L);
        deployment.setProject("project");
        deployment.setEnvironment("env");

        GlobalException exception;
        Mockito.when(this.deploymentViewRepository.findOne(Mockito.any())).thenReturn(Optional.empty());
        exception = null;
        try {
            this.service.deleteProgressDeployment(Map.of("environment", "test", "project", "test"));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.ENTITY_NOT_FOUND.getMessage(), exception.getError().getMessage());
        assertEquals("deployment", exception.getError().getField());
    }

    @Test
    void testDeleteProgressEmptyDeploymentProgress() {
        DeploymentView deployment = new DeploymentView();
        deployment.setId(1L);
        deployment.setProject("project");
        deployment.setEnvironment("env");

        GlobalException exception;
        Mockito.when(this.deploymentViewRepository.findOne(Mockito.any())).thenReturn(Optional.of(deployment));
        Mockito.when(this.deploymentProgressRepository.findByDeploymentId(Mockito.any())).thenReturn(Optional.empty());
        exception = null;
        try {
            this.service.deleteProgressDeployment(Map.of("environment", "test", "project", "test"));
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

        DeploymentView deployment = new DeploymentView();
        deployment.setId(1L);
        deployment.setProject("project");
        deployment.setEnvironment("environment");

        GlobalException exception;
        Mockito.when(this.deploymentViewRepository.findOne(Mockito.any())).thenReturn(Optional.of(deployment));
        Mockito.when(this.deploymentProgressRepository.findByDeploymentId(Mockito.any()))
                .thenReturn(Optional.of(new DeploymentProgress()));
        exception = null;
        try {
            this.service.deleteProgressDeployment(Map.of("environment", "test", "project", "test"));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
    }

    @Test
    void testCount() {
        GlobalException exception = null;
        try {
            this.service.count(null, "", "");
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(ErrorType.UNKNOWN_FIELD.getMessage(), exception.getMessage());

        Mockito.when(this.deploymentViewRepository.count(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(JsonNodeFactory.instance.objectNode());
        exception = null;
        JsonNode node = null;
        try {
            node = this.service.count(null, "id", "");
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
        assertNotNull(node);
    }


    @Test
    void testUpdate() throws IllegalAccessException, InvocationTargetException {
        Mockito.when(this.deploymentRepository.findById(Mockito.any())).thenReturn(Optional.of(new Deployment()));
        Mockito.when(this.deploymentRepository.save(Mockito.any())).thenReturn(new Deployment());
        GlobalException exception = null;
        try {
            service.update(1L, new Deployment());
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
    }
}
