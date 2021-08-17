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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.octo.helpers.EntityHelpers;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
class ArrayPredicateFilterTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void constructorTest() {
        ArrayPredicateFilter filter = new ArrayPredicateFilter(null, null);

        assertNull(filter.getName());
        assertEquals(0, filter.getValues().length);
        assertEquals(FilterType.Type.ARRAY.name(), filter.getType());

        filter = new ArrayPredicateFilter("test", "value");
        assertEquals("test", filter.getName());
        assertEquals(1, filter.getValues().length);
        assertEquals("value", filter.getValues()[0]);
        assertEquals(FilterType.Type.ARRAY.name(), filter.getType());
    }

    @Test
    void getPredicateTest() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<EntityHelpers> query = builder.createQuery(EntityHelpers.class);
        final Root<EntityHelpers> root = query.from(EntityHelpers.class);

        ArrayPredicateFilter filter = new ArrayPredicateFilter("name", "test");
        assertTrue(filter.extract());
        Predicate predicate = filter.getPredicate(builder, root, null);
        assertNotNull(predicate);
        assertFalse(filter.getIsNotOperator(0));

        filter = new ArrayPredicateFilter("name", "not_test");
        assertTrue(filter.extract());
        predicate = filter.getPredicate(builder, root, null);
        assertNotNull(predicate);
        assertTrue(filter.getIsNotOperator(0));
    }
}
