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
class AdministratorControllerTest extends JerseyTest {

    @Mock
    UserService service;

    @InjectMocks
    AdministratorController controller;

    @Override
    protected Application configure() {
        final ResourceConfig rc = new ResourceConfig().register(AdministratorController.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        this.bind(AdministratorControllerTest.this.controller).to(AdministratorController.class);
                    }
                });
        ;

        rc.property("contextConfigLocation", "classpath:application-context.xml");

        return rc;
    }

    @Test
    void testUpdatePassword() {
        Mockito.doNothing().when(service).updateDefaultAdminitratorPassword(Mockito.anyString());
        Response response = this.controller.updatePassword("test");

        assertNotNull(response);
        assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    void testUpdateEmail() {
        Mockito.doNothing().when(service).updateDefaultAdminitratorEmail(Mockito.anyString());
        Response response = this.controller.updateEmail("test");

        assertNotNull(response);
        assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }
}
