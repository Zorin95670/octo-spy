package com.octo.service;

import static org.junit.Assert.assertEquals;
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
import com.octo.model.dto.deployment.LastDeploymentDTO;
import com.octo.model.entity.DeploymentView;

public class DeploymentViewServiceTest {

    @Mock
    IDAO<DeploymentView, LastDeploymentDTO> deploymentViewDAO;

    @InjectMocks
    DeploymentViewService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFind() {

        Mockito.when(this.deploymentViewDAO.findAll()).thenReturn(new ArrayList<DeploymentView>());

        List<LastDeploymentDTO> result = service.find();

        assertNotNull(result);
        assertEquals(0, result.size());
    }

}
