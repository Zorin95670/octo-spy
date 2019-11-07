package com.octo.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.octo.model.dto.common.DefaultDTO;
import com.octo.model.entity.Project;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class ProjectDAOTest {

    @Autowired
    private IDAO<Project, DefaultDTO> projectDAO;

    @Test
    public void test() {
        assertEquals(Project.class, projectDAO.getType());
    }

}
