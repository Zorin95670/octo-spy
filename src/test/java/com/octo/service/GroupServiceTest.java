package com.octo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cji.dao.IDAO;
import com.cji.utils.predicate.filter.QueryFilter;
import com.octo.model.entity.Group;
import com.octo.model.entity.Project;
import com.octo.model.entity.ProjectGroup;

public class GroupServiceTest {

    @Mock
    IDAO<ProjectGroup, QueryFilter> projectGroupDAO;

    @Mock
    IDAO<Group, QueryFilter> groupDAO;

    @InjectMocks
    GroupService service;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

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

        Mockito.when(groupDAO.loadEntityById(Mockito.any(), Mockito.any())).thenReturn(group);
        Mockito.when(projectGroupDAO.save(Mockito.any())).thenReturn(null);

        Project masterProject = new Project();
        masterProject.setId(1L);
        assertNull(service.addProjectToGroup(masterProject, new Project()));
    }
}
