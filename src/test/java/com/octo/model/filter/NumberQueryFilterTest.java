package com.octo.model.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.octo.model.entity.Environment;
import com.octo.model.error.ErrorType;
import com.octo.model.exception.OctoException;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class NumberQueryFilterTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void constructorTest() {
        NumberQueryFilter<?> filter = new NumberQueryFilter<>(null, null);

        assertNull(filter.getFieldName());
        assertNull(filter.getFieldValue());
        assertEquals("number", filter.getType());
        assertNull(filter.getValue());

        filter = new NumberQueryFilter<>("test", "1");
        assertEquals("test", filter.getFieldName());
        assertEquals("1", filter.getFieldValue());
        assertEquals("number", filter.getType());
        assertNull(filter.getValue());
    }

    @Test
    public void extractTest() {
        NumberQueryFilter<?> filter = new NumberQueryFilter<>(null, null);
        assertFalse(filter.extract());

        filter = new NumberQueryFilter<>(null, "null");
        assertTrue(filter.extract());

        filter = new NumberQueryFilter<>(null, "1");
        assertTrue(filter.extract());

        filter = new NumberQueryFilter<>(null, "1,1");
        assertTrue(filter.extract());

        filter = new NumberQueryFilter<>(null, "1,2,3,4,5");
        assertTrue(filter.extract());

        filter = new NumberQueryFilter<>(null, "not1");
        assertTrue(filter.extract());

        filter = new NumberQueryFilter<>(null, "not1,2,3,4,5");
        assertTrue(filter.extract());
    }

    @Test
    public void getPredicateTest() {

        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Environment> query = builder.createQuery(Environment.class);
        final Root<Environment> root = query.from(Environment.class);

        NumberQueryFilter<?> filter = new NumberQueryFilter<>("id", "null");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root));

        filter = new NumberQueryFilter<>("id", "not1");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root));

        filter = new NumberQueryFilter<>("id", "1,1");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root));

        filter = new NumberQueryFilter<>("id", "not1,1");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root));
    }

    @Test
    public void testWithBadNumber() {
        OctoException exception = null;

        try {
            new NumberQueryFilter<>("test", "a").extract();
        } catch (final OctoException oe) {
            exception = oe;
        }

        assertNotNull(exception);
        assertEquals(ErrorType.BAD_FILTER_VALUE.getMessage(), exception.getMessage());
        exception = null;

        try {
            new NumberQueryFilter<>("test", "9999999999999999999999999").extract();
        } catch (final OctoException oe) {
            exception = oe;
        }

        assertNotNull(exception);
        assertEquals(ErrorType.BAD_FILTER_VALUE.getMessage(), exception.getMessage());
        exception = null;

        try {
            new NumberQueryFilter<>("test", "notnot").extract();
        } catch (final OctoException oe) {
            exception = oe;
        }

        assertNotNull(exception);
        assertEquals(ErrorType.BAD_FILTER_VALUE.getMessage(), exception.getMessage());
    }
}
