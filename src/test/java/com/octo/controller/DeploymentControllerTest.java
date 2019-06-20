package com.octo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.octo.model.dto.deployment.DeploymentDTO;
import com.octo.model.exception.OctoException;
import com.octo.service.DeploymentService;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class DeploymentControllerTest extends JerseyTest {

    @Mock
    DeploymentService service;

    @InjectMocks
    DeploymentController controller;

    @Override
    protected Application configure() {
        MockitoAnnotations.initMocks(this);
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
    public void testGetDeployment() throws OctoException {
        Mockito.when(this.service.loadById(Mockito.anyLong())).thenReturn(new DeploymentDTO());

        final Response response = this.controller.getDeployment(1L);

        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }

    @Test
    public void testCreateDeployment() throws OctoException {
        Mockito.when(this.service.save(Mockito.any())).thenReturn(new DeploymentDTO());

        final Response response = this.controller.createDeployment(null);

        assertNotNull(response);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }
}
