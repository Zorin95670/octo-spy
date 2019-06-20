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

import com.octo.dao.IDAO;
import com.octo.model.dto.project.NewProjectDTO;
import com.octo.model.dto.project.ProjectDTO;
import com.octo.model.entity.Project;
import com.octo.model.error.ErrorType;
import com.octo.model.exception.OctoException;

public class ProjectServiceTest {

    @Mock
    IDAO<Project> projectDAO;

    @InjectMocks
    ProjectService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadById() throws OctoException {
        // Test null ID
        OctoException exception = null;

        try {
            service.loadById(null);
        } catch (OctoException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.EMPTY_VALUE.getMessage(), exception.getError().getMessage());
        assertEquals("id", exception.getError().getField());
        assertNull(exception.getError().getValue());

        // Test unknow ID
        Mockito.when(this.projectDAO.loadById(Long.valueOf(1L))).thenReturn(null);
        exception = null;

        try {
            service.loadById(Long.valueOf(1L));
        } catch (OctoException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.ENTITY_NOT_FOUND.getMessage(), exception.getError().getMessage());
        assertEquals("id", exception.getError().getField());
        assertEquals(Long.valueOf(1L).toString(), exception.getError().getValue());

        // Test valid ID
        Mockito.when(this.projectDAO.loadById(Long.valueOf(2L))).thenReturn(new Project());
        exception = null;
        ProjectDTO dto = null;
        try {
            dto = service.loadById(Long.valueOf(2L));
        } catch (OctoException e) {
            exception = e;
        }
        assertNull(exception);
        assertNotNull(dto);
    }

    @Test
    public void testSave() throws OctoException {
        // Test null name
        OctoException exception = null;
        NewProjectDTO input = new NewProjectDTO();

        try {
            service.save(input);
        } catch (OctoException e) {
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
        } catch (OctoException e) {
            exception = e;
        }

        assertNull(exception);
        assertNotNull(dto);

    }
}
