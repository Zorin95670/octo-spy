package com.octo.service;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.octo.dao.IDAO;
import com.octo.model.entity.Environment;
import com.octo.utils.predicate.filter.QueryFilter;

@ExtendWith(MockitoExtension.class)
class EnvironmentServiceTest {

    @Mock
    IDAO<Environment, QueryFilter> environmentDAO;

    @InjectMocks
    EnvironmentService service;

    @Test
    void testFindAll() {
        List<Environment> list = new ArrayList<>();
        Mockito.when(this.environmentDAO.find(Mockito.any(), Mockito.any())).thenReturn(list);
        assertNotNull(service.findAll());
    }
}
