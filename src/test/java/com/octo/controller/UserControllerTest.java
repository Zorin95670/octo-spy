package com.octo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MultivaluedHashMap;
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
import org.postgresql.util.Base64;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.octo.model.dto.user.UserViewDTO;
import com.octo.service.UserService;
import com.octo.utils.Constants;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
class UserControllerTest extends JerseyTest {

    @Mock
    UserService service;

    @InjectMocks
    UserController controller;

    @Override
    protected Application configure() {
        final ResourceConfig rc = new ResourceConfig().register(UserController.class).register(new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(UserControllerTest.this.controller).to(UserController.class);
            }
        });;

        rc.property("contextConfigLocation", "classpath:application-context.xml");

        return rc;
    }

    @Test
    void testGetMyInformations() {
        ContainerRequestContext context = Mockito.mock(ContainerRequestContext.class);

        String encodedUser = Base64.encodeBytes(new String("login:password").getBytes());

        MultivaluedHashMap<String, String> headers = new MultivaluedHashMap<>();
        headers.add(Constants.AUTHORIZATION_PROPERTY,
                String.format("%s %s", Constants.AUTHENTICATION_SCHEME, encodedUser));

        Mockito.when(context.getHeaders()).thenReturn(headers);

        Mockito.when(this.service.getUser(Mockito.any())).thenReturn(new UserViewDTO());
        Mockito.when(this.service.getUserRoles(Mockito.any())).thenReturn(new ArrayList<>());
        final Response response = this.controller.getMyInformations(context);

        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }
}
