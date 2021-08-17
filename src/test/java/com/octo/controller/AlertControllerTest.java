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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.octo.model.dto.alert.AlertRecord;
import com.octo.service.AlertService;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
class AlertControllerTest extends JerseyTest {

    @Mock
    AlertService service;

    @InjectMocks
    AlertsController controller;

    @Override
    protected Application configure() {
        final ResourceConfig rc = new ResourceConfig().register(AlertsController.class).register(new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(AlertControllerTest.this.controller).to(AlertsController.class);
            }
        });;

        rc.property("contextConfigLocation", "classpath:application-context.xml");

        return rc;
    }

    @Test
    void testGetClients() {
        final List<AlertRecord> alerts = new ArrayList<>();
        Mockito.when(this.service.getAlerts()).thenReturn(alerts);
        Response response = this.controller.getAlerts();

        assertNotNull(response);
        assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());

        alerts.add(new AlertRecord(null, null, null));
        Mockito.when(this.service.getAlerts()).thenReturn(alerts);
        response = this.controller.getAlerts();

        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

    }
}
