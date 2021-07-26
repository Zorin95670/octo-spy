package com.octo.model.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class RessourceTest {

    @Test
    public void testConstructor() {
        List<String> list = null;
        Resource<String> resource = new Resource<String>(0l, list, 0, 0);

        assertEquals(Long.valueOf(0l), resource.getTotal());
        assertNull(resource.getResources());
        assertEquals(0, resource.getPage());
        assertEquals(0, resource.getCount());

        list = new ArrayList<>();
        resource = new Resource<String>(10l, list, 1, 2);

        assertEquals(Long.valueOf(10l), resource.getTotal());
        assertNotNull(resource.getResources());
        assertEquals(0, resource.getResources().size());
        assertEquals(1, resource.getPage());
        assertEquals(2, resource.getCount());
    }

    @Test
    public void testSetter() {
        List<String> list = null;
        final Resource<String> resource = new Resource<String>(0l, list, 0, 0);

        assertEquals(Long.valueOf(0l), resource.getTotal());
        assertNull(resource.getResources());
        assertEquals(0, resource.getPage());
        assertEquals(0, resource.getCount());

        list = new ArrayList<>();
        resource.setTotal(10l);
        resource.setResources(list);
        resource.setPage(1);
        resource.setCount(2);

        assertEquals(Long.valueOf(10l), resource.getTotal());
        assertNotNull(resource.getResources());
        assertEquals(0, resource.getResources().size());
        assertEquals(1, resource.getPage());
        assertEquals(2, resource.getCount());
    }

    @Test
    public void testGetStatus() {
        List<String> list = new ArrayList<>();

        assertEquals(200, new Resource<String>(0l, list, 0, 0).getStatus());
        assertEquals(200, new Resource<String>(-1l, list, 0, 0).getStatus());
        assertEquals(206, new Resource<String>(1l, list, 0, 0).getStatus());
    }
}
