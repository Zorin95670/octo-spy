package com.octo.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.octo.dao.IDAO;
import com.octo.utils.predicate.filter.QueryFilter;
import com.octo.model.entity.DeploymentProgress;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class DeploymentProgressDAOTest {

    @Autowired
    private IDAO<DeploymentProgress, QueryFilter> deploymentProgressDAO;

    @Test
    public void test() {
        assertEquals(DeploymentProgress.class, deploymentProgressDAO.getType());
    }

}
