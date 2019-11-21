package com.octo.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.octo.model.dto.common.DefaultDTO;
import com.octo.model.entity.DeploymentView;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class DeploymentViewDAOTest {

    @Autowired
    private IDAO<DeploymentView, DefaultDTO> deploymentViewDAO;

    @Test
    public void test() {
        assertEquals(DeploymentView.class, deploymentViewDAO.getType());
    }

    @Test
    public void testFindAll() {
        assertNotNull(deploymentViewDAO.findAll());
    }

}
