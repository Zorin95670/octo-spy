package com.octo.dao;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.octo.model.entity.ProjectGroup;
import com.octo.utils.predicate.filter.QueryFilter;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class ProjectGroupDAOTest {

    @Autowired
    private IDAO<ProjectGroup, QueryFilter> projectGroupDAO;

    @Test
    public void test() {
        assertEquals(ProjectGroup.class, projectGroupDAO.getType());
    }

}
