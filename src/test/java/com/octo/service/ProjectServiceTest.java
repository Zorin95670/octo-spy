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
    void testSaveGenerateException() {
        GlobalException exception = null;
        try {
            this.service.save(null);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getError().getMessage(), ErrorType.EMPTY_VALUE.getMessage());

        exception = null;
        try {
            this.service.save(new NewProjectRecord(null, null, false, null));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getError().getMessage(), ErrorType.EMPTY_VALUE.getMessage());
    }

    @Test
    void testSaveDuplicateMasterProject() {
        Mockito.when(this.projectViewDAO.load(Mockito.any())).thenReturn(Optional.of(new ProjectView()));

        GlobalException exception = null;
        try {
            this.service.save(new NewProjectRecord("test", null, true, null));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getError().getMessage(), ErrorType.WRONG_VALUE.getMessage());
    }

    @Test
    void testsaveMasterProject() {
        Mockito.when(this.projectViewDAO.load(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(this.projectDAO.save(Mockito.any())).thenReturn(new Project());
        Mockito.when(this.groupService.create(Mockito.any())).thenReturn(null);

        GlobalException exception = null;
        try {
            this.service.save(new NewProjectRecord("test", null, true, null));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
    }

    @Test
    void testSaveSubProjectWithEmptyMasterProject() {
        GlobalException exception = null;
        try {
            this.service.save(new NewProjectRecord("test", null, false, null));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getError().getMessage(), ErrorType.EMPTY_VALUE.getMessage());
    }

    @Test
    void testSaveDuplicateSubProject() {
        Mockito.when(this.projectViewDAO.load(Mockito.any())).thenReturn(Optional.of(new ProjectView()));

        GlobalException exception = null;
        try {
            this.service.save(new NewProjectRecord("test", null, false, "master"));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getError().getMessage(), ErrorType.WRONG_VALUE.getMessage());
    }

    @Test
    void testSaveSubProjectWithUnknowMasterProject() {
        Mockito.when(this.projectViewDAO.load(Mockito.any())).thenReturn(Optional.empty());
        GlobalException exception = null;
        try {
            this.service.save(new NewProjectRecord("test", null, false, "master"));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getError().getMessage(), ErrorType.ENTITY_NOT_FOUND.getMessage());
    }

    @Test
    void testSaveSubProject() {
        ProjectView master = new ProjectView();
        master.setId(1L);
        Mockito.when(this.projectViewDAO.load(Mockito.any())).thenReturn(Optional.empty(), Optional.of(master));
        Mockito.when(this.projectDAO.save(Mockito.any())).thenReturn(new Project());
        Mockito.when(this.groupService.addProjectToGroup(Mockito.anyLong(), Mockito.any())).thenReturn(null);

        GlobalException exception = null;
        try {
            this.service.save(new NewProjectRecord("test", null, false, "master"));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
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

    @Test
    void testUpdate() {
        Mockito.when(this.projectDAO.loadEntityById(Mockito.any())).thenReturn(null);
        Mockito.when(this.projectDAO.save(Mockito.any())).thenReturn(new Project());

        GlobalException exception = null;
        try {
            this.service.update(1L, new NewProjectRecord("test", "1,1,1", false, null));
        } catch (GlobalException e) {
            exception = e;
        }

        assertNotNull(exception);

        Mockito.when(this.projectDAO.loadEntityById(Mockito.any())).thenReturn(new Project());
        exception = null;
        try {
            this.service.update(1L, new NewProjectRecord("test", "1,1,1", false, null));
        } catch (GlobalException e) {
            exception = e;
        }

        assertNull(exception);
    }
}
