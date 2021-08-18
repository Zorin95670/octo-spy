package com.octo.utils.predicate.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.octo.helpers.EntityHelpers;
import com.octo.utils.predicate.PredicateOperator;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
class TextPredicateFilterTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void constructorTest() {
        TextPredicateFilter filter = new TextPredicateFilter(null, null);

        assertNull(filter.getName());
        assertEquals(0, filter.getValues().length);
        assertEquals(FilterType.Type.TEXT.name(), filter.getType());

        filter = new TextPredicateFilter("test", "value");
        assertEquals("test", filter.getName());
        assertEquals(1, filter.getValues().length);
        assertEquals("value", filter.getValues()[0]);
        assertEquals(FilterType.Type.TEXT.name(), filter.getType());
        assertTrue(filter.isSpecificOperator());
    }

    @Test
    void testGetPredicate() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<EntityHelpers> query = builder.createQuery(EntityHelpers.class);
        final Root<EntityHelpers> root = query.from(EntityHelpers.class);

        TextPredicateFilter filter = new TextPredicateFilter("name", "not_1");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));

        filter = new TextPredicateFilter("name", "1");
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

        filter = new TextPredicateFilter("name", "not_");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));
        assertEquals(PredicateOperator.EQUALS, filter.getOperator(0));

        filter = new TextPredicateFilter("name", "not_%");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));
        assertEquals(PredicateOperator.EQUALS, filter.getOperator(0));

        filter = new TextPredicateFilter("name", "lk1");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));
        assertFalse(filter.getIsNotOperator(0));
        assertEquals(PredicateOperator.LIKE, filter.getOperator(0));
        assertEquals("1", filter.getValue(0));

        filter = new TextPredicateFilter("name", "not_lktest");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));
        assertEquals(PredicateOperator.LIKE, filter.getOperator(0));
        assertTrue(filter.getIsNotOperator(0));
        assertEquals("test", filter.getValue(0));

        filter = new TextPredicateFilter("name", "not_lkt*es*t");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));
        assertEquals(PredicateOperator.LIKE, filter.getOperator(0));
        assertTrue(filter.getIsNotOperator(0));
        assertEquals("t%es%t", filter.getValue(0));
    }
}
