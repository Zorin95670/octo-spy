package com.octo.persistence.specification.filter;

import com.octo.helper.MockHelper;
import com.octo.persistence.model.Project;
import com.octo.persistence.specification.PredicateOperator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Tag("unit")
class PredicateFilterTest extends MockHelper {

    @Test
    void testConstructor() {
        PredicateFilterFake filter = new PredicateFilterFake(null, null);
        assertNull(filter.getName());
        assertEquals(0, filter.getValues().length);

        filter = new PredicateFilterFake("name", "test");
        assertEquals("name", filter.getName());
        assertEquals(1, filter.getValues().length);
        assertEquals("test", filter.getValues()[0]);

        assertTrue(PredicateOperator.get("bad").isEmpty());
        assertNotNull(filter.getOperators());
    }

    @Test
    void testSetOperatorFromValue() {
        PredicateFilterFake filter = new PredicateFilterFake("eq1", PredicateOperator.EQUALS.getValue());
        filter.extract();
        assertEquals(PredicateOperator.EQUALS, filter.getOperator(0));
        assertFalse(filter.getIsNotOperator(0));

        filter = new PredicateFilterFake(null, "not_null");
        filter.extract();
        assertEquals(PredicateOperator.NULL, filter.getOperator(0));
        assertTrue(filter.getIsNotOperator(0));

        filter = new PredicateFilterFake(null, "null");
        filter.extract();
        assertEquals(PredicateOperator.NULL, filter.getOperator(0));
        assertFalse(filter.getIsNotOperator(0));

        filter = new PredicateFilterFake(null, PredicateOperator.NULL.getValue());
        filter.extract();
        assertEquals(PredicateOperator.NULL, filter.getOperator(0));
    }

    @Test
    void testExtract() {
        PredicateFilterFake filter = new PredicateFilterFake(null, null);
        assertFalse(filter.extract());

        filter = new PredicateFilterFake(null, "not_");
        assertTrue(filter.extract());
        assertEquals(PredicateOperator.EQUALS, filter.getOperator(0));
        assertTrue(filter.getIsNotOperator(0));
        assertEquals("", filter.getValue(0));
    }

    @Test
    void getPredicateTest() {
        EntityManager entityManager = mockEntityManager(Project.class);
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Project> query = builder.createQuery(Project.class);
        final Root<Project> root = query.from(Project.class);

        PredicateFilterFake filter = new PredicateFilterFake("name", "test");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));

        filter = new PredicateFilterFake("name", "not_null");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));

        filter = new PredicateFilterFake("name", "null");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));

        filter = new PredicateFilterFake("name", "not_1");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));

        filter = new PredicateFilterFake("name", "1");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));
    }

    private class PredicateFilterFake extends PredicateFilter {

        public PredicateFilterFake(final String name, final String value) {
            super(name, value, FilterType.Type.TEXT);
        }
    }
}
