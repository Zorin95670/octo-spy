package com.octo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
import com.octo.model.entity.Project;

public class ProjectServiceTest {

    @Mock
    IDAO<Project, QueryFilter> projectDAO;

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
