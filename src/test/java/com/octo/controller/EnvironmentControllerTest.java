package com.octo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.octo.model.dto.environment.EnvironmentDTO;
import com.octo.service.EnvironmentService;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class EnvironmentControllerTest extends JerseyTest {

    @Mock
    EnvironmentService service;

    @InjectMocks
    EnvironmentController controller;

    @Override
    protected Application configure() {
        MockitoAnnotations.initMocks(this);
        final ResourceConfig rc = new ResourceConfig(EnvironmentController.class);

        rc.property("contextConfigLocation", "classpath:application-context.xml");

        return rc;
    }

    @Test
    public void getAllTest() throws JsonProcessingException {
        List<EnvironmentDTO> environments = new ArrayList<>();
        environments.add(new EnvironmentDTO());
        Mockito.when(this.service.findAll()).thenReturn(environments);
        final Response response = this.target("/environment").request().get();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        ArrayNode array = response.readEntity(ArrayNode.class);
        assertNotEquals(1, array.size());
    }
}
