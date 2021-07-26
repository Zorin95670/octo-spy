package com.octo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.octo.model.common.Resource;
import com.octo.model.dto.deployment.DeploymentDTO;
import com.octo.model.dto.deployment.LastDeploymentDTO;
import com.octo.service.DeploymentService;
import com.octo.service.LastDeploymentViewService;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class DeploymentControllerTest extends JerseyTest {

    @Mock
    DeploymentService service;

    @Mock
    LastDeploymentViewService viewService;

    @InjectMocks
    DeploymentController controller;

    @Override
    protected Application configure() {
        final ResourceConfig rc = new ResourceConfig().register(DeploymentController.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        this.bind(DeploymentControllerTest.this.controller).to(DeploymentController.class);
                    }
                });
        ;

        rc.property("contextConfigLocation", "classpath:application-context.xml");

        return rc;
    }

    @Test
    public void testGetDeployment() {
        Mockito.when(this.service.load(Mockito.anyLong())).thenReturn(new DeploymentDTO());

        final Response response = this.controller.getDeployment(1L);

        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }

    @Test
    public void testCreateDeployment() {
        Mockito.when(this.service.save(Mockito.any())).thenReturn(new DeploymentDTO());

        final Response response = this.controller.createDeployment(null);

        assertNotNull(response);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }

    @Test
    public void testDeleteDeployment() {
        Mockito.doNothing().when(this.service).delete(1L);
        final Response response = this.controller.deleteDeployment(1L);

        assertNotNull(response);
        assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetLastDeployments() {
        List<LastDeploymentDTO> expected = new ArrayList<>();
        Mockito.when(this.viewService.find(Mockito.any())).thenReturn(expected);
        final Response response = this.controller.getLastDeployments(Mockito.any());

        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expected, response.getEntity());

    }

    @Test
    public void testDeleteDeploymentProgress() {
        Mockito.doNothing().when(this.service).deleteProgressDeployment(Mockito.any());
        final Response response = this.controller.deleteProgressDeployment(null);

        assertNotNull(response);
        assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetDeployments() throws JsonProcessingException {
        Mockito.when(this.service.find(Mockito.any())).thenReturn(new Resource<>(5L, new ArrayList<>(), 0, 0));
        Response response = this.controller.getDeployments(null);

        assertNotNull(response);
        assertEquals(HttpStatus.PARTIAL_CONTENT.value(), response.getStatus());
        final String json = new ObjectMapper().writeValueAsString(response.getEntity());

        assertEquals("{\"total\":5,\"page\":0,\"count\":0,\"resources\":[]}", json);

        Mockito.when(this.service.find(Mockito.any())).thenReturn(new Resource<>(0L, new ArrayList<>(), 0, 0));
        response = this.controller.getDeployments(null);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
