package com.octo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.octo.dao.IDAO;
import com.octo.helpers.EntityHelpers;
import com.octo.helpers.EntityService;
import com.octo.helpers.EntityTestSearch;
import com.octo.model.dto.count.CountDTO;
import com.octo.model.dto.count.MultipleCountDTO;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
@Transactional
class MultipleCountServiceTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Mock
    IDAO<Object[], CountDTO> dao;

    @InjectMocks
    MultipleCountService service;

    @Autowired
    private EntityService testService;

    @BeforeEach
    void setUp() {
        testService.save("test1");
        testService.save("test2");
        testService.save("test3");
    }

    @Test
    void countShouldRaiseExceptionOnEmptyField() {
        GlobalException exception = null;

        // Test empty field
        final MultipleCountDTO dto = new MultipleCountDTO();

        try {
            this.service.count(EntityHelpers.class, dto, new EntityTestSearch());
        } catch (final GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertNull(exception.getError().getValue());
        assertEquals(ErrorType.EMPTY_VALUE.getMessage(), exception.getError().getMessage());
        assertEquals("field", exception.getError().getField());
    }

    @Test
    void countShouldRaiseExceptionOnUnknownField() {
        GlobalException exception = null;

        // Test empty field
        final MultipleCountDTO dto = new MultipleCountDTO();
        dto.setFields(List.of("bad"));

        try {
            this.service.count(EntityHelpers.class, dto, new EntityTestSearch());
        } catch (final GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertNull(exception.getError().getValue());
        assertEquals(ErrorType.UNKNOW_FIELD.getMessage(), exception.getError().getMessage());
        assertEquals("field", exception.getError().getField());
    }
}
