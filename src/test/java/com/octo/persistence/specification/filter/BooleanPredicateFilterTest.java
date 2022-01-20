package com.octo.persistence.specification.filter;

import com.octo.helper.MockHelper;
import com.octo.persistence.model.Project;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class BooleanPredicateFilterTest extends MockHelper {

    @Test
    void constructorTest() {
        BooleanPredicateFilter filter = new BooleanPredicateFilter(null, null);
        filter.extract();

        assertNull(filter.getName());
        assertEquals(0, filter.getValues().length);

        filter = new BooleanPredicateFilter("test", "true");
        filter.extract();
        assertEquals("test", filter.getName());
        assertEquals(1, filter.getValues().length);
        assertEquals("true", filter.getValues()[0]);
        assertFalse(filter.getIsNotOperator(0));

        filter = new BooleanPredicateFilter("test", "not_true");
        filter.extract();
        assertEquals("test", filter.getName());
        assertEquals(1, filter.getValues().length);
        assertEquals("true", filter.getValues()[0]);
        assertTrue(filter.getIsNotOperator(0));
    }

    @Test
    void getPredicateTest() {
        EntityManager entityManager = this.mockEntityManager(Project.class);
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Project> query = builder.createQuery(Project.class);
        final Root<Project> root = query.from(Project.class);

        BooleanPredicateFilter filter = new BooleanPredicateFilter("name", "test");
        assertTrue(filter.extract());
        Predicate predicate = filter.getPredicate(builder, root, null);
        assertNotNull(predicate);
        assertFalse(filter.getIsNotOperator(0));

        filter = new BooleanPredicateFilter("name", "not_test");
        assertTrue(filter.extract());
        predicate = filter.getPredicate(builder, root, null);
        assertNotNull(predicate);
        assertTrue(filter.getIsNotOperator(0));
    }
}
