package com.octo.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.octo.dao.IDAO;
import com.octo.model.dto.environment.EnvironmentDTO;
import com.octo.model.entity.Environment;

public class EnvironmentServiceTest {

    @Mock
    IDAO<Environment> dao;

    @InjectMocks
    EnvironmentService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll() {
        Mockito.when(this.dao.findAll()).thenReturn(new ArrayList<Environment>());

        List<EnvironmentDTO> list = service.findAll();
        assertNotNull(list);
        assertTrue(list.isEmpty());
    }
}
