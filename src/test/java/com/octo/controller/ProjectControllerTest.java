package com.octo.controller;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.octo.controller.model.QueryFilter;
import com.octo.helper.MockHelper;
import com.octo.model.project.ProjectRecord;
import com.octo.persistence.model.Project;
import com.octo.persistence.model.ProjectView;
import com.octo.service.ProjectService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@Tag("unit")
class ProjectControllerTest extends MockHelper {

    @Mock
    ProjectService service;

    @InjectMocks
    ProjectController controller;

    @Test
    void testGetProject() {
        Mockito.when(this.service.load(1L)).thenReturn(new ProjectView());
        final Response response = this.controller.getProject(1L);

        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }

    @Test
    void testGetProjects() {
        Mockito.when(this.service.findAll(Mockito.any(), Mockito.any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        final Response response = this.controller.getProjects(mockUriInfo(), new QueryFilter());

        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }

    @Test
    void testCount() {
        Mockito.when(this.service.count(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(JsonNodeFactory.instance.objectNode());
        final Response response = this.controller.count(mockUriInfo(), null, null);

        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }

    @Test
    void testCreateProject() {
        Mockito.when(this.service.save(Mockito.any())).thenReturn(new Project());
        final Response response = this.controller.createProject(new ProjectRecord(null, null, false, null));

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

    @Test
    void testUpdateProject() throws InvocationTargetException, IllegalAccessException {
        Mockito.doNothing().when(this.service).update(Mockito.anyLong(), Mockito.any());
        final Response response = this.controller.updateProject(1L, new Project());

        assertNotNull(response);
        assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }
}
