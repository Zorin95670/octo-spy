package com.octo.dao;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.octo.model.entity.LastDeploymentView;
import com.octo.utils.predicate.filter.QueryFilter;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
class LastDeploymentViewDAOTest {

    @Autowired
    private IDAO<LastDeploymentView, QueryFilter> lastDeploymentViewDAO;

    @Test
    void test() {
        assertEquals(LastDeploymentView.class, lastDeploymentViewDAO.getType());
    }
}
