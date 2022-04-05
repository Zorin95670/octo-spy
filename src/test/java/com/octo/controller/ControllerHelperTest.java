package com.octo.controller;

import com.octo.helper.MockHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedHashMap;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Tag("unit")
class ControllerHelperTest extends MockHelper {

    @Test
    void testGetFilters() {
        HashMap<String, String> map = new HashMap<>();
        assertEquals(map, ControllerHelper.getFilters(mockUriInfo()));
    }

    @Test
    void testGetStatus() {
        assertEquals(200, ControllerHelper.getStatus(new PageImpl<String>(List.of(), Pageable.ofSize(1), 1)));
        assertEquals(206, ControllerHelper.getStatus(new PageImpl<String>(List.of(), Pageable.ofSize(1), 2)));
    }

    @Test
    void testGetLogin() {
        MultivaluedHashMap<String, String> headers = new MultivaluedHashMap<>();
        ContainerRequestContext context = Mockito.mock(ContainerRequestContext.class);

        Mockito.when(context.getHeaders()).thenReturn(headers);
        assertNull(ControllerHelper.getLogin(context));
    }
}
