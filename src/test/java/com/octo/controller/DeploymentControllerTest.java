package com.octo.controller;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.octo.controller.model.QueryFilter;
import com.octo.helper.MockHelper;
import com.octo.persistence.model.DeploymentView;
import com.octo.service.DeploymentService;
import com.octo.service.LastDeploymentViewService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
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
class DeploymentControllerTest extends MockHelper {

    @Mock
    DeploymentService service;

    @Mock
    LastDeploymentViewService viewService;

    @InjectMocks
    DeploymentController controller;

    @Test
    void testGetDeployment() {
        Mockito.when(this.service.loadView(Mockito.anyLong())).thenReturn(new DeploymentView());

        final Response response = this.controller.getDeployment(1L);

        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }

    @Test
    void testCreateDeployment() {
        Mockito.when(this.service.save(Mockito.any())).thenReturn(new DeploymentView());

        final Response response = this.controller.createDeployment(null);

        assertNotNull(response);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }

    @Test
    void testDeleteDeployment() {
        Mockito.doNothing().when(this.service).delete(1L);
        final Response response = this.controller.deleteDeployment(1L);

        assertNotNull(response);
        assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    void testGetLastDeployments() {
        Mockito.when(this.viewService.find(Mockito.any(), Mockito.any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        final Response response = this.controller.getLastDeployments(this.mockUriInfo(), new QueryFilter());

        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void testDeleteDeploymentProgress() {
        Mockito.doNothing().when(this.service).deleteProgressDeployment(Mockito.any());
        final Response response = this.controller.deleteProgressDeployment(this.mockUriInfo());

        assertNotNull(response);
        assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    void testGetDeployments() {
        Mockito.when(this.service.find(Mockito.any(), Mockito.any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        Response response = this.controller.getDeployments(mockUriInfo(), new QueryFilter());

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void testCount() {
        Mockito.when(this.service.count(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(JsonNodeFactory.instance.objectNode());
        final Response response = this.controller.count(this.mockUriInfo(), null, null);

        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }

    @Test
    void testUpdateProject() throws IllegalAccessException, InvocationTargetException {
        Mockito.doNothing().when(this.service).update(Mockito.anyLong(), Mockito.any());
        final Response response = this.controller.updateProject(1L, null);

        assertNotNull(response);
        assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }
}
