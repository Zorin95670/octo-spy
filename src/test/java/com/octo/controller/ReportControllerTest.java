package com.octo.controller;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.octo.helper.MockHelper;
import com.octo.service.DeploymentReportViewService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@Tag("unit")
class ReportControllerTest extends MockHelper {

    @Mock
    DeploymentReportViewService service;

    @InjectMocks
    ReportController controller;

    @Test
    void testCount() {
        Mockito.when(this.service.count(Mockito.any(), Mockito.any())).thenReturn(JsonNodeFactory.instance.objectNode());
        final Response response = this.controller.count(mockUriInfo(), null);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
