package com.octo.controller;

import com.octo.controller.model.QueryFilter;
import com.octo.helper.MockHelper;
import com.octo.persistence.model.Dashboard;
import com.octo.service.DashboardService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@Tag("unit")
class DashboardControllerTest extends MockHelper {

    @Mock
    DashboardService service;

    @InjectMocks
    DashboardController controller;

    @Test
    void testGetDashboard() {
        ContainerRequestContext context = this.mockContext();
        Mockito.when(this.service.load(Mockito.any(), Mockito.anyLong())).thenReturn(new Dashboard());

        final Response response = this.controller.getDashboard(context, 1L);

        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }

    @Test
    void testGetDashboards() {
        ContainerRequestContext context = this.mockContext();
        Mockito.when(this.service.find(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        final Response response = this.controller.getDashboards(context, this.mockUriInfo(), new QueryFilter());

        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void testCreateDashboard() {
        ContainerRequestContext context = this.mockContext();
        Mockito.when(this.service.save(Mockito.any(), Mockito.any())).thenReturn(new Dashboard());

        final Response response = this.controller.createDashboard(context, null);

        assertNotNull(response);
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }

    @Test
    void testUpdateDashboard() throws IllegalAccessException, InvocationTargetException {
        ContainerRequestContext context = this.mockContext();
        Mockito.doNothing().when(this.service).update(Mockito.any(), Mockito.anyLong(), Mockito.any());
        final Response response = this.controller.updateDashboard(context, 1L, null);

        assertNotNull(response);
        assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    void testDeleteDashboard() {
        ContainerRequestContext context = this.mockContext();
        Mockito.doNothing().when(this.service).delete(Mockito.any(), Mockito.anyLong());
        final Response response = this.controller.deleteDashboard(context, 1L);

        assertNotNull(response);
        assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }
}
