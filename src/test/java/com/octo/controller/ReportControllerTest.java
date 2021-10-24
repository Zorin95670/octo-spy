package com.octo.controller;

import static org.junit.Assert.assertEquals;

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
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.octo.model.dto.count.MultipleCountDTO;
import com.octo.model.dto.deployment.SearchDeploymentReportViewDTO;
import com.octo.model.dto.environment.EnvironmentDTO;
import com.octo.service.MultipleCountService;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
class ReportControllerTest extends JerseyTest {

    @Mock
    MultipleCountService service;

    @InjectMocks
    ReportController controller;

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
        final ResourceConfig rc = new ResourceConfig().register(ReportController.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        this.bind(ReportControllerTest.this.controller).to(ReportController.class);
                    }
                });;

        rc.property("contextConfigLocation", "classpath:application-context.xml");

        return rc;
    }

    @Test
    void testCount() throws JsonProcessingException {
        List<EnvironmentDTO> environments = new ArrayList<>();
        environments.add(new EnvironmentDTO());
        Mockito.when(this.service.count(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(JsonNodeFactory.instance.objectNode());
        final Response response = this.controller.count(new MultipleCountDTO(), new SearchDeploymentReportViewDTO());

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
