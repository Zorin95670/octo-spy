package com.octo.service;

import com.octo.controller.model.QueryFilter;
import com.octo.model.environment.EnvironmentRecord;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.persistence.model.Environment;
import com.octo.persistence.repository.EnvironmentRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
class EnvironmentServiceTest {

    @Mock
    EnvironmentRepository environmentRepository;

    @InjectMocks
    EnvironmentServiceImpl service;

    @Test
    void testFindAll() {
        Mockito.when(this.environmentRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl(List.of()));
        assertNotNull(service.findAll(Map.of(), new QueryFilter().getPagination()));
    }

    @Test
    void testSaveWithDuplicateName() {
        Mockito.when(this.environmentRepository.findByNameIgnoreCase(Mockito.any()))
                .thenReturn(Optional.of(new Environment()));
        GlobalException exception = null;
        try {
            service.save(new EnvironmentRecord("test", 0));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getMessage(), ErrorType.WRONG_VALUE.getMessage());
    }

    @Test
    void testSave() {
        Mockito.when(this.environmentRepository.findByNameIgnoreCase(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(this.environmentRepository.save(Mockito.any())).thenReturn(new Environment());
        assertNotNull(service.save(new EnvironmentRecord("name", 0)));
    }

    @Test
    void testUpdateDuplicateName() {
        Mockito.when(this.environmentRepository.findByNameIgnoreCaseAndIdIsNot(Mockito.any(), Mockito.any()))
                .thenReturn(Optional.of(new Environment()));
        GlobalException exception = null;
        try {
            service.update(1L, new EnvironmentRecord("test", 0));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getMessage(), ErrorType.WRONG_VALUE.getMessage());
    }

    @Test
    void testUpdate() {
        Environment environment = new Environment();
        environment.setId(1L);
        environment.setName("test");
        Mockito.when(this.environmentRepository.findByNameIgnoreCaseAndIdIsNot(Mockito.any(), Mockito.any()))
                .thenReturn(Optional.empty());
        Mockito.when(this.environmentRepository.save(Mockito.any())).thenReturn(environment);
        Mockito.when(this.environmentRepository.findById(Mockito.any())).thenReturn(Optional.of(environment));
        GlobalException exception = null;
        try {
            service.update(1L, new EnvironmentRecord("test", 0));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
        exception = null;
        try {
            service.update(1L, new EnvironmentRecord(null, 0));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
    }

    @Test
    void testDelete() {
        Mockito.when(this.environmentRepository.findById(Mockito.any())).thenReturn(Optional.of(new Environment()));
        Mockito.doNothing().when(environmentRepository).deleteById(Mockito.any());
        GlobalException exception = null;
        try {
            service.delete(1L);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
    }
}
