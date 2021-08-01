package com.octo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.octo.model.dto.project.NewProjectRecord;
import com.octo.model.dto.project.ProjectDTO;
import com.octo.service.CountService;
import com.octo.service.ProjectService;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
class ProjectControllerTest extends JerseyTest {

    @Mock
    ProjectService service;

    @Mock
    CountService countService;

    @InjectMocks
    ProjectController controller;

    @Override
    protected Application configure() {
        final ResourceConfig rc = new ResourceConfig().register(ProjectController.class).register(new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(ProjectControllerTest.this.controller).to(ProjectController.class);
            }
        });
        ;

        rc.property("contextConfigLocation", "classpath:application-context.xml");

        return rc;
    }

    @Test
    void testGetProject() {
        Mockito.when(this.service.load(1L)).thenReturn(new ProjectDTO());
        final Response response = this.controller.getProject(1L);

        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }

    @Test
    void testGetProjects() {
        Mockito.when(this.service.findAll(Mockito.any())).thenReturn(new ArrayList<>());
        final Response response = this.controller.getProjects(null);

        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }

    @Test
    void testCount() {
        Mockito.when(this.countService.count(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(JsonNodeFactory.instance.objectNode());
        final Response response = this.controller.count(null, null);

        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }

    @Test
    void testCreateProject() {
        Mockito.when(this.service.save(Mockito.any())).thenReturn(new ProjectDTO());
        final Response response = this.controller.createProject(new NewProjectRecord(null, false, null));

        assertNotNull(response);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }

    @Test
    void testDeleteProject() {
        Mockito.doNothing().when(this.service).delete(1L);
        final Response response = this.controller.deleteProject(1L);

        assertNotNull(response);
        assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }
}
