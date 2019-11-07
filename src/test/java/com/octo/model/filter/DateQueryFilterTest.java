package com.octo.model.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.octo.model.entity.Deployment;
import com.octo.model.error.ErrorType;
import com.octo.model.exception.OctoException;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class DateQueryFilterTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void constructorTest() {
        DateQueryFilter filter = new DateQueryFilter(null, null);

        assertNull(filter.getFieldName());
        assertNull(filter.getFieldValue());
        assertEquals("date", filter.getType());
        assertNull(filter.getValue());
        assertNull(filter.getSubValue());
        assertNull(filter.getOperator());

        filter = new DateQueryFilter("test", "value");
        assertEquals("test", filter.getFieldName());
        assertEquals("value", filter.getFieldValue());
        assertEquals("date", filter.getType());
        assertNull(filter.getValue());
        assertNull(filter.getSubValue());
    }

    public OctoException extractAndGetException(final DateQueryFilter filter) {
        try {
            filter.extract();
        } catch (final OctoException e) {
            return e;
        }
        return null;
    }

    @Test
    public void extractTest() {
        DateQueryFilter filter = new DateQueryFilter(null, null);
        OctoException exception = null;

        exception = this.extractAndGetException(filter);
        assertFalse(filter.extract());

        filter = new DateQueryFilter(null, "");
        exception = this.extractAndGetException(filter);
        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.BAD_FILTER_VALUE.getMessage(), exception.getError().getMessage());

        filter = new DateQueryFilter(null, "bad");
        exception = this.extractAndGetException(filter);
        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.BAD_FILTER_VALUE.getMessage(), exception.getError().getMessage());

        filter = new DateQueryFilter(null, "2018-01-01 00:00:00ab2019-01-01 00:00:00");
        exception = this.extractAndGetException(filter);
        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.BAD_FILTER_OPERATOR.getMessage(), exception.getError().getMessage());

        filter = new DateQueryFilter(null, "2019-01-01 00:00:00");
        assertTrue(filter.extract());
        assertEquals("eq", filter.getOperator());
        assertNull(filter.getSubValue());
        assertEquals("2019-01-01 00:00:00", filter.getValue());

        filter = new DateQueryFilter(null, "gt2019-01-01 00:00:00");
        assertTrue(filter.extract());
        assertEquals("gt", filter.getOperator());
        assertNull(filter.getSubValue());
        assertEquals("2019-01-01 00:00:00", filter.getValue());

        filter = new DateQueryFilter(null, "lt2019-01-01 00:00:00");
        assertTrue(filter.extract());
        assertEquals("lt", filter.getOperator());
        assertNull(filter.getSubValue());
        assertEquals("2019-01-01 00:00:00", filter.getValue());

        filter = new DateQueryFilter(null, "2018-01-01 00:00:00bt2019-01-01 00:00:00");
        assertTrue(filter.extract());
        assertEquals("bt", filter.getOperator());
        assertEquals("2018-01-01 00:00:00", filter.getSubValue());
        assertEquals("2019-01-01 00:00:00", filter.getValue());

        filter = new DateQueryFilter(null, "bt2019-01-01 00:00:00");
        exception = this.extractAndGetException(filter);
        assertNotNull(exception);
        assertNotNull(exception.getError());
        assertEquals(ErrorType.EMPTY_VALUE.getMessage(), exception.getError().getMessage());
    }

    @Test
    public void getPredicateTest() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Deployment> query = builder.createQuery(Deployment.class);
        final Root<Deployment> root = query.from(Deployment.class);

        DateQueryFilter filter = new DateQueryFilter("insertDate", "2019-01-01 00:00:00");
        assertTrue(filter.extract());
        Predicate predicate = filter.getPredicate(builder, root);
        assertNotNull(predicate);

        filter = new DateQueryFilter("insertDate", "lt2019-01-01 00:00:00");
        assertTrue(filter.extract());
        predicate = filter.getPredicate(builder, root);
        assertNotNull(predicate);

        filter = new DateQueryFilter("insertDate", "gt2019-01-01 00:00:00");
        assertTrue(filter.extract());
        predicate = filter.getPredicate(builder, root);
        assertNotNull(predicate);

        filter = new DateQueryFilter("insertDate", "2019-01-01 00:00:00bt2019-01-01 00:00:00");
        assertTrue(filter.extract());
        predicate = filter.getPredicate(builder, root);
        assertNotNull(predicate);
    }

    @Test
    public void getDateTest() {
        final DateQueryFilter filter = new DateQueryFilter(null, null);
        Exception exception = null;
        try {
            filter.getDate(null);
        } catch (final OctoException e) {
            exception = e;
        }
        assertNotNull(exception);

        Date date = null;
        exception = null;
        try {
            date = filter.getDate("2019-12-01 00:00:00");
        } catch (final OctoException e) {
            exception = e;
        }

        assertNull(exception);
        assertNotNull(date);
    }
}
