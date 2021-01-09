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

import com.cji.dao.IDAO;
import com.cji.utils.predicate.filter.QueryFilter;
import com.octo.model.entity.LastDeploymentView;

public class LastDeploymentViewServiceTest {

    @Mock
    IDAO<LastDeploymentView, QueryFilter> lastDeploymentViewDAO;

    @InjectMocks
    LastDeploymentViewService service;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        List<LastDeploymentView> list = new ArrayList<>();
        Mockito.when(this.lastDeploymentViewDAO.find(Mockito.any(), Mockito.any())).thenReturn(list);
        assertNotNull(service.find());
    }

}
