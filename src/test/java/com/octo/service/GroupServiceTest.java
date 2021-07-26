package com.octo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.octo.dao.IDAO;
import com.octo.model.entity.Group;
import com.octo.model.entity.Project;
import com.octo.model.entity.ProjectGroup;
import com.octo.utils.predicate.filter.QueryFilter;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class GroupServiceTest {

    @Mock
    IDAO<ProjectGroup, QueryFilter> projectGroupDAO;

    @Mock
    IDAO<Group, QueryFilter> groupDAO;

    @InjectMocks
    GroupService service;

    @Test
    public void testCreate() {
        Group group = new Group();
        group.setId(1L);

        Mockito.when(groupDAO.save(Mockito.any())).thenReturn(group);
        Mockito.when(projectGroupDAO.save(Mockito.any())).thenReturn(null);

        assertEquals(group, service.create(null));
    }

    @Test
    public void testAddProjectToGroup() {
        Group group = new Group();
        group.setId(1L);

        System.out.println(groupDAO == null);
        Mockito.when(groupDAO.loadEntityById(Mockito.any(), Mockito.any())).thenReturn(group);
        Mockito.when(projectGroupDAO.save(Mockito.any())).thenReturn(null);

        Project masterProject = new Project();
        masterProject.setId(1L);
        assertNull(service.addProjectToGroup(masterProject, new Project()));
    }
}
