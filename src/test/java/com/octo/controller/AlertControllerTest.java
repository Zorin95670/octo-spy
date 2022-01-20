package com.octo.controller;

import com.octo.model.alert.Alert;
import com.octo.service.AlertService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@Tag("unit")
class AlertControllerTest {

    @Mock
    AlertService service;

    @InjectMocks
    AlertsController controller;

    @Test
    void testGetAlerts() {
        final List<Alert> alerts = new ArrayList<>();
        alerts.add(new Alert(null, null, null));

        Mockito.when(this.service.getAlerts()).thenReturn(alerts);
        Response response = this.controller.getAlerts();

        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }
}