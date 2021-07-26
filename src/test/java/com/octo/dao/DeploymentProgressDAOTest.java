package com.octo.dao;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.octo.model.entity.DeploymentProgress;
import com.octo.utils.predicate.filter.QueryFilter;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class DeploymentProgressDAOTest {

    @Autowired
    private IDAO<DeploymentProgress, QueryFilter> deploymentProgressDAO;

    @Test
    public void test() {
        assertEquals(DeploymentProgress.class, deploymentProgressDAO.getType());
    }

}
