package com.octo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.octo.config.Configuration;
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
class InfoControllerTest {

    @Mock
    Configuration configuration;

    @InjectMocks
    InfoController controller;

    @Test
    void getVersionFunctionalTest() throws JsonProcessingException {
        Mockito.when(configuration.getProject()).thenReturn("project");
        Mockito.when(configuration.getVersion()).thenReturn("version");
        Mockito.when(configuration.getEnvironment()).thenReturn("environment");

        ObjectMapper mapper = new ObjectMapper();

        final Response response = this.controller.getVersion();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("{\"project\":\"project\",\"version\":\"version\",\"environment\":\"environment\"," +
                "\"client\":null}", mapper.writeValueAsString(response.getEntity()));
    }
}