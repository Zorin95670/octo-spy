package com.octo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Optional;

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
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.utils.predicate.filter.QueryFilter;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
class GroupServiceTest {

    @Mock
    IDAO<ProjectGroup, QueryFilter> projectGroupDAO;

    @Mock
    IDAO<Group, QueryFilter> groupDAO;

    @InjectMocks
    GroupService service;

    @Test
    void testCreate() {
        Group group = new Group();
        group.setId(1L);

        Mockito.when(groupDAO.save(Mockito.any())).thenReturn(group);
        Mockito.when(projectGroupDAO.save(Mockito.any())).thenReturn(null);

        assertEquals(group, service.create(null));
    }

    @Test
    void testAddProjectToGroupWithUnknowGroup() {
        Mockito.when(groupDAO.load(Mockito.any())).thenReturn(Optional.empty());
        GlobalException exception = null;
        try {
            this.service.addProjectToGroup(1L, null);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getError().getMessage(), ErrorType.ENTITY_NOT_FOUND.getMessage());
    }

    @Test
    void testAddProjectToGroup() {
        Mockito.when(groupDAO.load(Mockito.any())).thenReturn(Optional.of(new Group()));
        Mockito.when(projectGroupDAO.save(Mockito.any())).thenReturn(null);

        assertNull(service.addProjectToGroup(1L, new Project()));
    }
}
