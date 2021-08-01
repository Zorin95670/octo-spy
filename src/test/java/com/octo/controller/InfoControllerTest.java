package com.octo.controller;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.octo.model.dto.common.ProjectInformationRecord;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
class InfoControllerTest extends JerseyTest {

    @Autowired
    InfoController controller;

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
        final ResourceConfig rc = new ResourceConfig(InfoController.class);

        rc.property("contextConfigLocation", "classpath:application-context.xml");

        return rc;
    }

    @Test
    void getVersionFunctionalTest() throws JsonProcessingException {
        final ProjectInformationRecord version = this.controller.getVersion();
        ObjectMapper mapper = new ObjectMapper();

        final Response response = this.target("/info").request().get();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(mapper.writeValueAsString(version), response.readEntity(String.class));
    }
}