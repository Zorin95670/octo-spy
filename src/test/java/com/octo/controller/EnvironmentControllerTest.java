package com.octo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.octo.controller.EnvironmentController;
import com.octo.controller.InfoController;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class EnvironmentControllerTest extends JerseyTest {

    @Autowired
    InfoController controller;

    @Override
    protected Application configure() {
        final ResourceConfig rc = new ResourceConfig(EnvironmentController.class);

        rc.property("contextConfigLocation", "classpath:application-context.xml");

        return rc;
    }

    @Test
    public void getAllTest() throws JsonProcessingException {
        final Response response = this.target("/environment").request().get();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        ArrayNode environments = response.readEntity(ArrayNode.class);
        assertNotEquals(4, environments.size());
    }
}
