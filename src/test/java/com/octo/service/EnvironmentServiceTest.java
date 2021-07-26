package com.octo.service;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.octo.dao.IDAO;
import com.octo.utils.predicate.filter.QueryFilter;
import com.octo.model.entity.Environment;

public class EnvironmentServiceTest {

    @Mock
    IDAO<Environment, QueryFilter> environmentDAO;

    @InjectMocks
    EnvironmentService service;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        List<Environment> list = new ArrayList<>();
        Mockito.when(this.environmentDAO.find(Mockito.any(), Mockito.any())).thenReturn(list);
        assertNotNull(service.findAll());
    }
}
