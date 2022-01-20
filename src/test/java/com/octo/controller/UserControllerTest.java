package com.octo.controller;

import com.octo.model.common.Constants;
import com.octo.model.user.UserViewDTO;
import com.octo.service.UserService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@Tag("unit")
class UserControllerTest {

    @Mock
    UserService service;

    @InjectMocks
    UserController controller;

    @Test
    void testGetMyInformations() {
        ContainerRequestContext context = Mockito.mock(ContainerRequestContext.class);

        String encodedUser = Base64.getUrlEncoder().encodeToString("login:password".getBytes());

        MultivaluedHashMap<String, String> headers = new MultivaluedHashMap<>();
        headers.add(Constants.AUTHORIZATION_PROPERTY,
                String.format("%s %s", Constants.AUTHENTICATION_BASIC_SCHEME, encodedUser));

        Mockito.when(context.getHeaders()).thenReturn(headers);

        Mockito.when(this.service.getUser(Mockito.any())).thenReturn(new UserViewDTO());
        Mockito.when(this.service.getUserRoles(Mockito.any())).thenReturn(new ArrayList<>());
        final Response response = this.controller.getMyInformation(context);

        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }

    @Test
    void testGetToken() {
        ContainerRequestContext context = Mockito.mock(ContainerRequestContext.class);

        String encodedUser = Base64.getUrlEncoder().encodeToString("login:password".getBytes());

        MultivaluedHashMap<String, String> headers = new MultivaluedHashMap<>();
        headers.add(Constants.AUTHORIZATION_PROPERTY,
                String.format("%s %s", Constants.AUTHENTICATION_BASIC_SCHEME, encodedUser));

        Mockito.when(context.getHeaders()).thenReturn(headers);
        Mockito.when(this.service.getUserToken(Mockito.any())).thenReturn(new PageImpl<>(List.of("token1", "token2")));
        final Response response = this.controller.getToken(context);

        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        Page<String> page = (Page<String>) response.getEntity();
        assertEquals("token1,token2", page.stream().collect(Collectors.joining(",")));
    }

    @Test
    void testCreateToken() throws NoSuchAlgorithmException {
        ContainerRequestContext context = Mockito.mock(ContainerRequestContext.class);

        String encodedUser = Base64.getUrlEncoder().encodeToString("login:password".getBytes());

        MultivaluedHashMap<String, String> headers = new MultivaluedHashMap<>();
        headers.add(Constants.AUTHORIZATION_PROPERTY,
                String.format("%s %s", Constants.AUTHENTICATION_BASIC_SCHEME, encodedUser));

        Mockito.when(context.getHeaders()).thenReturn(headers);
        Mockito.when(this.service.createToken(Mockito.any(), Mockito.any())).thenReturn("token");
        final Response response = this.controller.createToken(context, "token");

        assertNotNull(response);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals("{\"token\":\"token\"}", response.getEntity().toString());
    }

    @Test
    void testDeleteToken() {
        ContainerRequestContext context = Mockito.mock(ContainerRequestContext.class);

        String encodedUser = Base64.getUrlEncoder().encodeToString("login:password".getBytes());

        MultivaluedHashMap<String, String> headers = new MultivaluedHashMap<>();
        headers.add(Constants.AUTHORIZATION_PROPERTY,
                String.format("%s %s", Constants.AUTHENTICATION_BASIC_SCHEME, encodedUser));

        Mockito.when(context.getHeaders()).thenReturn(headers);
        Mockito.doNothing().when(this.service).deleteToken(Mockito.any(), Mockito.any());
        final Response response = this.controller.deleteToken(context, null);

        assertNotNull(response);
        assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }
}
