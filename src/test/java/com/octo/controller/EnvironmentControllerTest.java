package com.octo.controller;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.octo.model.dto.environment.EnvironmentDTO;
import com.octo.model.dto.environment.NewEnvironmentRecord;
import com.octo.service.EnvironmentService;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
class EnvironmentControllerTest extends JerseyTest {

    @Mock
    EnvironmentService service;

    @InjectMocks
    EnvironmentController controller;

    @BeforeEach
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @AfterEach
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected Application configure() {
        final ResourceConfig rc = new ResourceConfig().register(EnvironmentController.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        this.bind(EnvironmentControllerTest.this.controller).to(EnvironmentController.class);
                    }
                });;

        rc.property("contextConfigLocation", "classpath:application-context.xml");

        return rc;
    }

    @Test
    void testGetAll() throws JsonProcessingException {
        List<EnvironmentDTO> environments = new ArrayList<>();
        environments.add(new EnvironmentDTO());
        Mockito.when(this.service.findAll()).thenReturn(environments);
        final Response response = this.target("/environments").request().get();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        ArrayNode array = response.readEntity(ArrayNode.class);
        assertEquals(1, array.size());
    }

    @Test
    void testCreateEnvironment() {
        Mockito.when(this.service.save(Mockito.any())).thenReturn(new EnvironmentDTO());
        final Response response = this.controller.createEnvironment(new NewEnvironmentRecord(null, 0));

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void testUpdateEnvironment() throws IllegalAccessException, InvocationTargetException {
        Mockito.doNothing().when(service).update(Mockito.any(), Mockito.any());
        final Response response = this.controller.updateEnvironment(1L, new NewEnvironmentRecord(null, 0));

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    void testDeleteEnvironment() throws IllegalAccessException, InvocationTargetException {
        Mockito.doNothing().when(service).delete(Mockito.any());
        final Response response = this.controller.deleteEnvironment(1L);

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }
}
