package com.octo.controller;

import com.octo.service.UserService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@Tag("unit")
class AdministratorControllerTest {

    @Mock
    UserService service;

    @InjectMocks
    AdministratorController controller;

    @Test
    void testUpdatePassword() {
        Mockito.doNothing().when(service).updateDefaultAdministratorPassword(Mockito.anyString());
        Response response = this.controller.updatePassword("test");

        assertNotNull(response);
        assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    void testUpdateEmail() {
        Mockito.doNothing().when(service).updateDefaultAdministratorEmail(Mockito.anyString());
        Response response = this.controller.updateEmail("test");

        assertNotNull(response);
        assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }
}