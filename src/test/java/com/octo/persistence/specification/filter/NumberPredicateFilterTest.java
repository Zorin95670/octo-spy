package com.octo.persistence.specification.filter;

import com.octo.helper.MockHelper;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.persistence.model.Project;
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
class NumberPredicateFilterTest extends MockHelper {

    @Test
    void constructorTest() {
        NumberPredicateFilter filter = new NumberPredicateFilter(null, null);

        assertNull(filter.getName());
        assertEquals(0, filter.getValues().length);

        filter = new NumberPredicateFilter("test", "value");
        assertEquals("test", filter.getName());
        assertEquals(1, filter.getValues().length);
        assertEquals("value", filter.getValues()[0]);
    }

    @Test
    void extractTest() {
        NumberPredicateFilter filter = new NumberPredicateFilter(null, null);
        assertFalse(filter.extract());

        filter = new NumberPredicateFilter(null, "null");
        assertTrue(filter.extract());

        filter = new NumberPredicateFilter(null, "not_null");
        assertTrue(filter.extract());

        filter = new NumberPredicateFilter(null, "1");
        assertTrue(filter.extract());

        filter = new NumberPredicateFilter(null, "1|1");
        assertTrue(filter.extract());

        filter = new NumberPredicateFilter(null, "1|2|3|4|5");
        assertTrue(filter.extract());

        filter = new NumberPredicateFilter(null, "not_1");
        assertTrue(filter.extract());

        filter = new NumberPredicateFilter(null, "not_1|2|not_3|4|5|not_null");
        assertTrue(filter.extract());
    }

    @Test
    void testWithBadNumber() {
        GlobalException exception = null;

        try {
            new NumberPredicateFilter("test", "a").extract();
        } catch (final GlobalException hre) {
            exception = hre;
        }

        assertNotNull(exception);
        assertEquals(ErrorType.WRONG_FILTER_VALUE.getMessage(), exception.getMessage());
        exception = null;

        try {
            new NumberPredicateFilter("test", "9999999999999999999999999").extract();
        } catch (final GlobalException hre) {
            exception = hre;
        }

        assertNotNull(exception);
        assertEquals(ErrorType.WRONG_FILTER_VALUE.getMessage(), exception.getMessage());
        exception = null;

        try {
            new NumberPredicateFilter("test", "notnot").extract();
        } catch (final GlobalException hre) {
            exception = hre;
        }

        assertNotNull(exception);
        assertEquals(ErrorType.WRONG_FILTER_VALUE.getMessage(), exception.getMessage());
    }

    @Test
    void testGetPredicate() {
        EntityManager entityManager = mockEntityManager(Project.class);
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Project> query = builder.createQuery(Project.class);
        final Root<Project> root = query.from(Project.class);

        NumberPredicateFilter filter = new NumberPredicateFilter("name", "not_1");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));

        filter = new NumberPredicateFilter("name", "1");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));

        filter = new NumberPredicateFilter("name", "1|not_2");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));

        filter = new NumberPredicateFilter("name", "not_1|not_2");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));

        filter = new NumberPredicateFilter("name", "1|2");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));

        filter = new NumberPredicateFilter("name", "1|2|null");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));
    }
}
