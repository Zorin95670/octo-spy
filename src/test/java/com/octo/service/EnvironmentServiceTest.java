package com.octo.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
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
import com.octo.model.dto.environment.NewEnvironmentRecord;
import com.octo.model.entity.Environment;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.utils.predicate.filter.QueryFilter;

@ExtendWith(MockitoExtension.class)
class EnvironmentServiceTest {

    @Mock
    IDAO<Environment, QueryFilter> environmentDAO;

    @InjectMocks
    EnvironmentService service;

    @Test
    void testFindAll() {
        List<Environment> list = new ArrayList<>();
        Mockito.when(this.environmentDAO.find(Mockito.any(), Mockito.any())).thenReturn(list);
        assertNotNull(service.findAll());
    }

    @Test
    void testSaveWithEmptyName() {
        GlobalException exception = null;
        try {
            service.save(null);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getMessage(), ErrorType.EMPTY_VALUE.getMessage());
        exception = null;
        try {
            service.save(new NewEnvironmentRecord(null, 0));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getMessage(), ErrorType.EMPTY_VALUE.getMessage());
    }

    @Test
    void testSaveWithDuplicateName() {
        Mockito.when(this.environmentDAO.load(Mockito.any())).thenReturn(Optional.of(new Environment()));
        GlobalException exception = null;
        try {
            service.save(new NewEnvironmentRecord("test", 0));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getMessage(), ErrorType.WRONG_VALUE.getMessage());
    }

    @Test
    void testSave() {
        Mockito.when(this.environmentDAO.load(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(this.environmentDAO.save(Mockito.any())).thenReturn(new Environment());
        assertNotNull(service.save(new NewEnvironmentRecord("name", 0)));
    }

    @Test
    void testUpdateEmptyName() throws IllegalAccessException, InvocationTargetException {
        GlobalException exception = null;
        try {
            service.update(1L, new NewEnvironmentRecord("", 0));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getMessage(), ErrorType.EMPTY_VALUE.getMessage());
    }

    @Test
    void testUpdateDuplicateName() throws IllegalAccessException, InvocationTargetException {
        Mockito.when(this.environmentDAO.load(Mockito.any())).thenReturn(Optional.of(new Environment()));
        GlobalException exception = null;
        try {
            service.update(1L, new NewEnvironmentRecord("test", 0));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getMessage(), ErrorType.WRONG_VALUE.getMessage());
    }

    @Test
    void testUpdate() throws IllegalAccessException, InvocationTargetException {
        Mockito.when(this.environmentDAO.load(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(this.environmentDAO.save(Mockito.any())).thenReturn(new Environment());
        Mockito.when(this.environmentDAO.loadEntityById(Mockito.any())).thenReturn(new Environment());
        GlobalException exception = null;
        try {
            service.update(1L, new NewEnvironmentRecord("test", 0));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
        exception = null;
        try {
            service.update(1L, new NewEnvironmentRecord(null, 0));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
    }

    @Test
    void testDelete() {
        Mockito.when(this.environmentDAO.loadEntityById(Mockito.any())).thenReturn(new Environment());
        Mockito.doNothing().when(environmentDAO).delete(Mockito.any());
        GlobalException exception = null;
        try {
            service.delete(1L);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
    }
}
