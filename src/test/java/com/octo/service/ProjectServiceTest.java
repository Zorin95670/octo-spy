package com.octo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.octo.dao.IDAO;
import com.octo.model.dto.project.NewProjectRecord;
import com.octo.model.dto.project.ProjectDTO;
import com.octo.model.entity.Group;
import com.octo.model.entity.Project;
import com.octo.model.entity.ProjectView;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.utils.predicate.filter.QueryFilter;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    IDAO<Project, QueryFilter> projectDAO;

    @Mock
    IDAO<ProjectView, QueryFilter> projectViewDAO;

    @Mock
    IGroupService groupService;

    @InjectMocks
    ProjectService service;

    @Test
    void testLoad() {
        Mockito.when(this.projectDAO.loadEntityById(Mockito.any())).thenReturn(new Project());
        assertNotNull(service.load(1L));
    }

    @Test
    void testSave() {
        // Test null name
        GlobalException exception = null;

        try {
            service.save(null);
        } catch (GlobalException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.EMPTY_VALUE.getMessage(), exception.getError().getMessage());
        assertEquals("name", exception.getError().getField());
        assertNull(exception.getError().getValue());

        exception = null;
        NewProjectRecord input = new NewProjectRecord(null, false, null);

        try {
            service.save(input);
        } catch (GlobalException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.EMPTY_VALUE.getMessage(), exception.getError().getMessage());
        assertEquals("name", exception.getError().getField());
        assertNull(exception.getError().getValue());

        // Test all good
        exception = null;
        input = new NewProjectRecord("Project test", false, null);

        Mockito.when(this.projectDAO.save(Mockito.any())).thenReturn(new Project());
        ProjectDTO dto = null;

        try {
            dto = service.save(input);
        } catch (GlobalException e) {
            exception = e;
        }

        assertNull(exception);
        assertNotNull(dto);
    }

    @Test
    void saveMasterProject() {
        NewProjectRecord dto = new NewProjectRecord("test", true, null);

        Mockito.when(projectDAO.save(Mockito.any())).thenReturn(new Project());
        Mockito.when(groupService.create(Mockito.any())).thenReturn(new Group());

        assertNotNull(service.save(dto));
        Mockito.verify(groupService).create(Mockito.any());
    }

    @Test
    void saveGroupProject() {
        NewProjectRecord dto = new NewProjectRecord("test", false, "master");

        Mockito.when(projectDAO.save(Mockito.any())).thenReturn(new Project());
        Mockito.when(projectDAO.load(Mockito.any())).thenReturn(Optional.empty());

        GlobalException exception = null;
        try {
            service.save(dto);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.ENTITY_NOT_FOUND.getMessage(), exception.getError().getMessage());
        assertEquals("project", exception.getError().getField());
        assertEquals("master", exception.getError().getValue());

        Mockito.when(projectDAO.load(Mockito.any())).thenReturn(Optional.of(new Project()));
        exception = null;
        try {
            service.save(dto);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
        Mockito.verify(groupService).addProjectToGroup(Mockito.any(), Mockito.any());
    }

    @Test
    void testDelete() {
        Mockito.doNothing().when(this.projectDAO).delete(Mockito.any());

        GlobalException exception = null;
        try {
            this.service.delete(1L);
        } catch (GlobalException e) {
            exception = e;
        }

        assertNull(exception);
    }

    @Test
    void testFindAll() {
        List<ProjectView> expected = new ArrayList<>();
        Mockito.when(this.projectViewDAO.find(Mockito.any(), Mockito.anyBoolean())).thenReturn(expected);

        assertNotNull(this.service.findAll(null));
    }
}
