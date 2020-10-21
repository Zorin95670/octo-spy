package com.octo.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.cji.dao.IDAO;
import com.cji.utils.predicate.filter.QueryFilter;
import com.octo.model.entity.Deployment;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class DeploymentDAOTest {

    @Autowired
    private IDAO<Deployment, QueryFilter> deploymentDAO;

    @Test
    public void test() {
        assertEquals(Deployment.class, deploymentDAO.getType());
    }

}
