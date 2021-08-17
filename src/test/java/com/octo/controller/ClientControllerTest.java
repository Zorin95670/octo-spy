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

import com.octo.service.ClientService;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
class ClientControllerTest extends JerseyTest {

    @Mock
    ClientService service;

    @InjectMocks
    ClientController controller;

    @Override
    protected Application configure() {
        final ResourceConfig rc = new ResourceConfig().register(ClientController.class).register(new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(ClientControllerTest.this.controller).to(ClientController.class);
            }
        });;

        rc.property("contextConfigLocation", "classpath:application-context.xml");

        return rc;
    }

    @Test
    void testGetClients() {
        Mockito.when(this.service.findAll()).thenReturn(new ArrayList<>());
        final Response response = this.controller.getClients();

        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }
}
