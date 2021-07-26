package com.octo.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.octo.dao.IDAO;
import com.octo.utils.predicate.filter.QueryFilter;
import com.octo.model.entity.Group;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class GroupDAOTest {

    @Autowired
    private IDAO<Group, QueryFilter> groupDAO;

    @Test
    public void test() {
        assertEquals(Group.class, groupDAO.getType());
    }

}
