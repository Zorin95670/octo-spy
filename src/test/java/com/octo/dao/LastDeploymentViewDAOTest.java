package com.octo.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.cji.dao.IDAO;
import com.cji.utils.predicate.filter.QueryFilter;
import com.octo.model.entity.LastDeploymentView;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class LastDeploymentViewDAOTest {

    @Autowired
    private IDAO<LastDeploymentView, QueryFilter> lastDeploymentViewDAO;

    @Test
    public void test() {
        assertEquals(LastDeploymentView.class, lastDeploymentViewDAO.getType());
    }
}
