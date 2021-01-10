package com.octo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cji.dao.IDAO;
import com.cji.models.error.ErrorType;
import com.cji.models.error.GlobalException;
import com.cji.utils.predicate.filter.QueryFilter;
import com.octo.model.dto.project.NewProjectDTO;
import com.octo.model.dto.project.ProjectDTO;
import com.octo.model.entity.Group;
import com.octo.model.entity.Project;

public class ProjectServiceTest {

    @Mock
    IDAO<Project, QueryFilter> projectDAO;

    @Mock
    IGroupService groupService;

    @InjectMocks
    ProjectService service;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoad() {
        Mockito.when(this.projectDAO.loadEntityById(Mockito.any())).thenReturn(new Project());
        assertNotNull(service.load(1L));
    }

    @Test
    public void testSave() {
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
        NewProjectDTO input = new NewProjectDTO();

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
        input.setName("Project test");

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
    public void saveMasterProject() {
        NewProjectDTO dto = new NewProjectDTO();
        dto.setIsMaster(true);
        dto.setName("test");

        Mockito.when(projectDAO.save(Mockito.any())).thenReturn(new Project());
        Mockito.when(groupService.create(Mockito.any())).thenReturn(new Group());

        assertNotNull(service.save(dto));
        Mockito.verify(groupService).create(Mockito.any());
    }

    @Test
    public void saveGroupProject() {
        NewProjectDTO dto = new NewProjectDTO();
        dto.setIsMaster(false);
        dto.setName("test");
        dto.setMasterName("master");

        Mockito.when(projectDAO.save(Mockito.any())).thenReturn(new Project());
        Mockito.when(projectDAO.load(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(groupService.create(Mockito.any())).thenReturn(new Group());

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
    public void testDelete() {
        Mockito.when(this.projectDAO.loadById(Mockito.any())).thenReturn(new Project());
        Mockito.doNothing().when(this.projectDAO).delete(Mockito.any());

        GlobalException exception = null;
        try {
            this.service.delete(1L);
        } catch (GlobalException e) {
            exception = e;
        }

        assertNull(exception);
    }
}
