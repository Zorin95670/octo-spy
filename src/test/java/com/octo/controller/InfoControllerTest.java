package com.octo.controller;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.octo.model.dto.common.ProjectInformation;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class InfoControllerTest extends JerseyTest {

    @Autowired
    InfoController controller;

    @Override
    protected Application configure() {
        final ResourceConfig rc = new ResourceConfig(InfoController.class);

        rc.property("contextConfigLocation", "classpath:application-context.xml");

        return rc;
    }

    @Test
    public void getVersionFunctionalTest() throws JsonProcessingException {
        final ProjectInformation version = this.controller.getVersion();
        ObjectMapper mapper = new ObjectMapper();

        final Response response = this.target("/info").request().get();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(mapper.writeValueAsString(version), response.readEntity(String.class));
    }
}