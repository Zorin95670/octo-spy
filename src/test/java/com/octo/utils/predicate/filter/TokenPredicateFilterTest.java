package com.octo.utils.predicate.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
class TokenPredicateFilterTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void constructorTest() {
        TokenPredicateFilter filter = new TokenPredicateFilter(null, null);

        assertNull(filter.getName());
        assertEquals(0, filter.getValues().length);
        assertEquals(FilterType.Type.TOKEN.name(), filter.getType());
    }

    @Test
    void testGetPredicate() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<EntityHelpers> query = builder.createQuery(EntityHelpers.class);
        final Root<EntityHelpers> root = query.from(EntityHelpers.class);

        TokenPredicateFilter filter = new TokenPredicateFilter("token", "test");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));
        assertEquals(PredicateOperator.EQUALS, filter.getOperator(0));

        filter = new TokenPredicateFilter("token", "not_test");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));
        assertEquals(PredicateOperator.EQUALS, filter.getOperator(0));
    }
}
