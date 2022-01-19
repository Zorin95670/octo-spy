package com.octo.service;

import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.persistence.repository.ProjectViewRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
class ServiceHelperTest {

    @Mock
    ProjectViewRepository projectViewRepository;

    @InjectMocks
    ProjectServiceImpl service;

    @Test
    void testLoadEntityById() {
        Mockito.when(projectViewRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        GlobalException exception = null;
        try {
            service.load(null);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(ErrorType.EMPTY_VALUE.getMessage(), exception.getMessage());
        assertEquals("id", exception.getError().getField());

        exception = null;
        try {
            service.load(1L);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(ErrorType.ENTITY_NOT_FOUND.getMessage(), exception.getMessage());
        assertEquals("id", exception.getError().getField());
        assertEquals("1", exception.getError().getValue());

    }

}
