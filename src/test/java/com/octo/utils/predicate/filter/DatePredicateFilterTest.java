package com.octo.utils.predicate.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

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
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.utils.predicate.PredicateOperator;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
class DatePredicateFilterTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void constructorTest() {
        DatePredicateFilter filter = new DatePredicateFilter(null, null);

        assertNull(filter.getName());
        assertEquals(0, filter.getValues().length);
        assertEquals(FilterType.Type.DATE.name(), filter.getType());

        filter = new DatePredicateFilter("test", "value");
        assertEquals("test", filter.getName());
        assertEquals(1, filter.getValues().length);
        assertEquals("value", filter.getValues()[0]);
        assertEquals(FilterType.Type.DATE.name(), filter.getType());
    }

    @Test
    void extractTest() {
        assertFalse(new DatePredicateFilter(null, null).extract());

        GlobalException exception = null;
        try {
            new DatePredicateFilter("name", "bad").extract();
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(ErrorType.WRONG_FILTER_VALUE.getMessage(), exception.getError().getMessage());

        DatePredicateFilter filter = new DatePredicateFilter(null, "2019-01-01 00:00:00");
        assertTrue(filter.extract());
        assertEquals(PredicateOperator.EQUALS, filter.getOperator(0));
        assertNull(filter.getSubValue(0));
        assertEquals("2019-01-01 00:00:00", filter.getValue(0));

        filter = new DatePredicateFilter(null, "gt2019-01-01 00:00:00");
        assertTrue(filter.extract());
        assertEquals(PredicateOperator.SUPERIOR, filter.getOperator(0));
        assertNull(filter.getSubValue(0));
        assertEquals("2019-01-01 00:00:00", filter.getValue(0));

        filter = new DatePredicateFilter(null, "lt2019-01-01 00:00:00");
        assertTrue(filter.extract());
        assertEquals(PredicateOperator.INFERIOR, filter.getOperator(0));
        assertNull(filter.getSubValue(0));
        assertEquals("2019-01-01 00:00:00", filter.getValue(0));

        filter = new DatePredicateFilter(null, "2018-01-01 00:00:00bt2019-01-01 00:00:00");
        assertTrue(filter.extract());
        assertEquals(PredicateOperator.BETWEEN, filter.getOperator(0));
        assertEquals("2018-01-01 00:00:00", filter.getSubValue(0));
        assertEquals("2019-01-01 00:00:00", filter.getValue(0));

        filter = new DatePredicateFilter(null, "null");
        assertTrue(filter.extract());
        assertEquals(PredicateOperator.NULL, filter.getOperator(0));
        assertNull(filter.getSubValue(0));
        assertEquals("null", filter.getValue(0));

        filter = new DatePredicateFilter(null, "not_null");
        assertTrue(filter.extract());
        assertEquals(PredicateOperator.NULL, filter.getOperator(0));
        assertTrue(filter.getIsNotOperator(0));
        assertNull(filter.getSubValue(0));
        assertEquals("null", filter.getValue(0));

        exception = null;
        try {
            new DatePredicateFilter(null, "bt2019-01-01 00:00:00").extract();
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.EMPTY_VALUE.getMessage(), exception.getError().getMessage());
    }

    @Test
    void getSpecificOperatorTest() {
        GlobalException exception = null;
        try {
            new DatePredicateFilter(null, "2019-01-01 00:00:00aa2019-01-01 00:00:00").extract();
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.WRONG_FILTER_OPERATOR.getMessage(), exception.getError().getMessage());
        exception = null;
        try {
            new DatePredicateFilter(null, "not_2019-01-01 00:00:00").extract();
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
    }

    @Test
    void getDateTest() {
        final DatePredicateFilter filter = new DatePredicateFilter(null, null);
        GlobalException exception = null;
        try {
            filter.getDate(null);
        } catch (final GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);

        Date date = null;
        exception = null;
        try {
            date = filter.getDate("2019-12-01 00:00:00");
        } catch (final GlobalException e) {
            exception = e;
        }

        assertNull(exception);
        assertNotNull(date);
    }

    @Test
    void getPredicateTest() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<EntityHelpers> query = builder.createQuery(EntityHelpers.class);
        final Root<EntityHelpers> root = query.from(EntityHelpers.class);

        DatePredicateFilter filter = new DatePredicateFilter("name", "2019-01-01 00:00:00|not_2018-01-01 00:00:00");
        assertTrue(filter.extract());
        Predicate predicate = filter.getPredicate(builder, root, null);
        assertNotNull(predicate);

        filter = new DatePredicateFilter("name", "lt2019-01-01 00:00:00");
        assertTrue(filter.extract());
        predicate = filter.getPredicate(builder, root, null);
        assertNotNull(predicate);

        filter = new DatePredicateFilter("name", "gt2019-01-01 00:00:00");
        assertTrue(filter.extract());
        predicate = filter.getPredicate(builder, root, null);
        assertNotNull(predicate);

        filter = new DatePredicateFilter("name", "2019-01-01 00:00:00bt2019-01-02 00:00:00");
        assertTrue(filter.extract());
        predicate = filter.getPredicate(builder, root, null);
        assertNotNull(predicate);
        assertEquals(1, filter.getValues().length);
        assertEquals(1, filter.getSubValues().length);
        assertEquals("2019-01-02 00:00:00", filter.getValue(0));
        assertEquals("2019-01-01 00:00:00", filter.getSubValue(0));

        filter = new DatePredicateFilter("name", "NULL");
        assertTrue(filter.extract());
        predicate = filter.getPredicate(builder, root, null);
        assertNotNull(predicate);

        filter = new DatePredicateFilter("name", "NOT_NULL");
        assertTrue(filter.extract());
        predicate = filter.getPredicate(builder, root, null);
        assertNotNull(predicate);

        filter = new DatePredicateFilter("name", "NOT_2019-01-01 00:00:00");
        assertTrue(filter.extract());
        predicate = filter.getPredicate(builder, root, null);
        assertNotNull(predicate);

        filter = new DatePredicateFilter("name", "not_2019-01-01 00:00:00bt2019-01-02 00:00:00");
        assertTrue(filter.extract());
        predicate = filter.getPredicate(builder, root, null);
        assertNotNull(predicate);
        assertEquals(1, filter.getValues().length);
        assertEquals(1, filter.getSubValues().length);
        assertEquals("2019-01-02 00:00:00", filter.getValue(0));
        assertEquals("2019-01-01 00:00:00", filter.getSubValue(0));
    }
}
