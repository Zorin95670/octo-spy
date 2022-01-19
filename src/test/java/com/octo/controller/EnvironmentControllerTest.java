package com.octo.controller;

import com.octo.controller.model.QueryFilter;
import com.octo.helper.MockHelper;
import com.octo.model.environment.EnvironmentRecord;
import com.octo.persistence.model.Environment;
import com.octo.service.EnvironmentService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@Tag("unit")
class EnvironmentControllerTest extends MockHelper {

    @Mock
    EnvironmentService service;

    @InjectMocks
    EnvironmentController controller;

    @Test
    void testGetAll() {
        Mockito.when(this.service.findAll(Mockito.any(), Mockito.any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        final Response response = this.controller.getAll(mockUriInfo(), new QueryFilter());


        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void testCreateEnvironment() {
        Mockito.when(this.service.save(Mockito.any())).thenReturn(new Environment());
        final Response response = this.controller.createEnvironment(new EnvironmentRecord(null, 0));

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void testUpdateEnvironment() {
        Mockito.doNothing().when(service).update(Mockito.any(), Mockito.any());
        final Response response = this.controller.updateEnvironment(1L, new EnvironmentRecord(null, 0));

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    void testDeleteEnvironment() {
        Mockito.doNothing().when(service).delete(Mockito.any());
        final Response response = this.controller.deleteEnvironment(1L);

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }
}
