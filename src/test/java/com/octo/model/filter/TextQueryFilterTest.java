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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.octo.model.entity.Environment;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class TextQueryFilterTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void constructorTest() {
        TextQueryFilter filter = new TextQueryFilter(null, null);

        assertNull(filter.getFieldName());
        assertNull(filter.getFieldValue());
        assertEquals("text", filter.getType());
        assertNull(filter.getValue());

        filter = new TextQueryFilter("test", "value");
        assertEquals("test", filter.getFieldName());
        assertEquals("value", filter.getFieldValue());
        assertEquals("text", filter.getType());
        assertNull(filter.getValue());
    }

    @Test
    public void extractTest() {
        final TextQueryFilter filter = new TextQueryFilter(null, null);

        assertFalse(filter.extract());
        assertNull(filter.getValue());

        filter.setFieldValue("test");
        assertTrue(filter.extract());
        assertEquals("test", filter.getValue());
    }

    @Test
    public void getPredicateTest() {
        final TextQueryFilter filter = new TextQueryFilter("name", "test");

        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Environment> query = builder.createQuery(Environment.class);
        final Root<Environment> root = query.from(Environment.class);

        assertTrue(filter.extract());
        final Predicate predicate = filter.getPredicate(builder, root);

        assertNotNull(predicate);
    }
}
