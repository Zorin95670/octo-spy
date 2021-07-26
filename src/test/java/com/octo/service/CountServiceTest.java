package com.octo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.octo.dao.IDAO;
import com.octo.helpers.EntityHelpers;
import com.octo.helpers.EntityService;
import com.octo.helpers.EntityTestSearch;
import com.octo.model.dto.count.CountDTO;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
@Transactional
public class CountServiceTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Mock
    IDAO<Object[], CountDTO> dao;

    @InjectMocks
    CountService service;

    @Autowired
    private EntityService testService;

    @BeforeEach
    public void setUp() {
        testService.save("test1");
        testService.save("test2");
        testService.save("test3");
    }

    @Test
    public void countTest() {
        Mockito.when(dao.getEntityManager()).thenReturn(entityManager);
        GlobalException exception = null;

        // Test empty field
        final CountDTO dto = new CountDTO();

        try {
            this.service.count(EntityHelpers.class, dto, new EntityTestSearch());
        } catch (final GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertNull(exception.getError().getValue());
        assertEquals(ErrorType.EMPTY_VALUE.getMessage(), exception.getError().getMessage());
        assertEquals("field", exception.getError().getField());

        // Test bad field
        exception = null;
        dto.setField("bad");

        try {
            this.service.count(EntityHelpers.class, dto, new EntityTestSearch());
        } catch (final GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(ErrorType.UNKNOW_FIELD.getMessage(), exception.getError().getMessage());
        assertEquals("field", exception.getError().getField());
        assertEquals("bad", exception.getError().getValue());

        // Test simple without value
        exception = null;
        dto.setField("name");

        try {
            this.service.count(EntityHelpers.class, dto, new EntityTestSearch());
        } catch (final GlobalException e) {
            exception = e;
        }
        assertNull(exception);

        // Test with values
        dto.setValue("a,b");

        JsonNode json = this.service.count(EntityHelpers.class, dto, new EntityTestSearch());
        ObjectNode expected = JsonNodeFactory.instance.objectNode();
        expected.put("a", 0);
        expected.put("b", 0);
        assertEquals(expected, json);
        exception = null;
        dto.setField("name");

        try {
            this.service.count(EntityHelpers.class, dto, new EntityTestSearch());
        } catch (final GlobalException e) {
            exception = e;
        }
        assertNull(exception);

        // Test with values
        dto.setValue("a");

        json = this.service.count(EntityHelpers.class, dto, new EntityTestSearch());
        expected = JsonNodeFactory.instance.objectNode();
        expected.put("a", 0);
        assertEquals(expected, json);
    }

    @Test
    public void testConvertValueToObject() {
        class Test {
            @SuppressWarnings("unused") // Used in tests below
            public boolean testBoolean;
            @SuppressWarnings("unused") // Used in tests below
            public String testString;
        }

        // Test String value
        assertNull(service.convertValueToObject(Test.class, "testString", null));
        assertEquals("", service.convertValueToObject(Test.class, "testString", ""));
        assertEquals("test", service.convertValueToObject(Test.class, "testString", "test"));

        // Test Boolean value
        assertNull(service.convertValueToObject(Test.class, "testBoolean", null));
        assertEquals(Boolean.TRUE, service.convertValueToObject(Test.class, "testBoolean", "true"));
        assertEquals(Boolean.FALSE, service.convertValueToObject(Test.class, "testBoolean", "false"));

        // Test throw exception on bad field name
        GlobalException exception = null;
        try {
            service.convertValueToObject(Test.class, "bad", null);
        } catch (final GlobalException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.WRONG_FIELD.getMessage(), exception.getError().getMessage());
        assertEquals("field", exception.getError().getField());
        assertEquals("bad", exception.getError().getValue());

        // Test throw exception on bad value
        exception = null;
        try {
            service.convertValueToObject(Test.class, "testBoolean", "bad");
        } catch (final GlobalException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.WRONG_FIELD.getMessage(), exception.getError().getMessage());
        assertEquals("value", exception.getError().getField());
        assertEquals("bad", exception.getError().getValue());
    }
}
