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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.octo.dao.IDAO;
import com.octo.model.entity.LastDeploymentView;
import com.octo.utils.predicate.filter.QueryFilter;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
class LastDeploymentViewServiceTest {

    @Mock
    IDAO<LastDeploymentView, QueryFilter> lastDeploymentViewDAO;

    @InjectMocks
    LastDeploymentViewService service;

    @Test
    void testFindAll() {
        List<LastDeploymentView> list = new ArrayList<>();
        Mockito.when(this.lastDeploymentViewDAO.find(Mockito.any(), Mockito.any())).thenReturn(list);
        assertNotNull(service.find(null));
    }

}
