package com.octo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

import com.octo.service.UserService;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
class AlertControllerTest extends JerseyTest {

    @Mock
    UserService service;

    @InjectMocks
    AlertsController controller;

    @Override
    protected Application configure() {
        final ResourceConfig rc = new ResourceConfig().register(AlertsController.class).register(new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(AlertControllerTest.this.controller).to(AlertsController.class);
            }
        });
        ;

        rc.property("contextConfigLocation", "classpath:application-context.xml");

        return rc;
    }

    @Test
    void testGetClients() {
        Mockito.when(this.service.isSecureAdministrator()).thenReturn(true);
        Mockito.when(this.service.isValidAdministratorEmail()).thenReturn(true);
        Response response = this.controller.getAlerts();

        assertNotNull(response);
        assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());

        Mockito.when(this.service.isSecureAdministrator()).thenReturn(false);
        Mockito.when(this.service.isValidAdministratorEmail()).thenReturn(false);
        response = this.controller.getAlerts();

        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

    }
}
