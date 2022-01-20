package com.octo.service;

import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.persistence.model.Group;
import com.octo.persistence.model.Project;
import com.octo.persistence.model.ProjectGroup;
import com.octo.persistence.repository.GroupRepository;
import com.octo.persistence.repository.ProjectGroupRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@Tag("unit")
class GroupServiceTest {

    @Mock
    ProjectGroupRepository projectGroupRepository;

    @Mock
    GroupRepository groupRepository;

    @InjectMocks
    GroupServiceImpl service;

    @Test
    void testSave() {
        Group group = new Group();
        group.setId(1L);

        Mockito.when(groupRepository.save(Mockito.any())).thenReturn(group);
        Mockito.when(projectGroupRepository.save(Mockito.any())).thenReturn(null);

        assertEquals(group, service.save(null));
    }

    @Test
    void testAddProjectToGroupWithUnknownGroup() {
        Mockito.when(groupRepository.findByMasterProjectId(Mockito.any())).thenReturn(Optional.empty());
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
        Mockito.when(groupRepository.findByMasterProjectId(Mockito.any())).thenReturn(Optional.of(new Group()));
        Mockito.when(projectGroupRepository.save(Mockito.any())).thenReturn(new ProjectGroup());

        assertNotNull(service.addProjectToGroup(1L, new Project()));
    }
}
