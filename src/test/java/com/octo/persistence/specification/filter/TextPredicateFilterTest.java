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
class TextPredicateFilterTest extends MockHelper {

    @Test
    void constructorTest() {
        TextPredicateFilter filter = new TextPredicateFilter(null, null);

        assertNull(filter.getName());
        assertEquals(0, filter.getValues().length);

        filter = new TextPredicateFilter("test", "value");
        assertEquals("test", filter.getName());
        assertEquals(1, filter.getValues().length);
        assertEquals("value", filter.getValues()[0]);
    }

    @Test
    void testGetPredicate() {
        EntityManager entityManager = mockEntityManager(Project.class);
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Project> query = builder.createQuery(Project.class);
        final Root<Project> root = query.from(Project.class);

        TextPredicateFilter filter = new TextPredicateFilter("name", "1");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));
        assertEquals(PredicateOperator.EQUALS, filter.getOperator(0));

        filter = new TextPredicateFilter("name", "");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));
        assertEquals(PredicateOperator.EQUALS, filter.getOperator(0));

        filter = new TextPredicateFilter("name", "%");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));
        assertEquals(PredicateOperator.EQUALS, filter.getOperator(0));

        filter = new TextPredicateFilter("name", "lk1");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));
        assertFalse(filter.getIsNotOperator(0));
        assertEquals(PredicateOperator.LIKE, filter.getOperator(0));
        assertEquals("1", filter.getValue(0));
    }


    @Test
    void testGetPredicateNot() {
        EntityManager entityManager = mockEntityManager(Project.class);
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Project> query = builder.createQuery(Project.class);
        final Root<Project> root = query.from(Project.class);

        TextPredicateFilter filter = new TextPredicateFilter("name", "not_1");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));

        filter = new TextPredicateFilter("name", "not_");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));
        assertEquals(PredicateOperator.EQUALS, filter.getOperator(0));

        filter = new TextPredicateFilter("name", "not_%");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));
        assertEquals(PredicateOperator.EQUALS, filter.getOperator(0));

        filter = new TextPredicateFilter("name", "not_lktest");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));
        assertEquals(PredicateOperator.LIKE, filter.getOperator(0));
        assertTrue(filter.getIsNotOperator(0));
        assertEquals("TEST", filter.getValue(0));

        filter = new TextPredicateFilter("name", "not_lkt*es*t");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));
        assertEquals(PredicateOperator.LIKE, filter.getOperator(0));
        assertTrue(filter.getIsNotOperator(0));
        assertEquals("T%ES%T", filter.getValue(0));

    }
}
