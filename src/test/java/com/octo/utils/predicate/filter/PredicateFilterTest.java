package com.octo.utils.predicate.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

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
import com.octo.helpers.EntityTestSearch;
import com.octo.utils.predicate.PredicateOperator;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
class PredicateFilterTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void testConstructor() {
        PredicateFilterFake filter = new PredicateFilterFake(null, null);
        assertNull(filter.getName());
        assertEquals(0, filter.getValues().length);
        assertEquals(FilterType.Type.TEXT.name(), filter.getType());

        filter = new PredicateFilterFake("name", "test");
        assertEquals("name", filter.getName());
        assertEquals(1, filter.getValues().length);
        assertEquals("test", filter.getValues()[0]);
        assertEquals(FilterType.Type.TEXT.name(), filter.getType());

        assertFalse(filter.isSpecificOperatorDefault());
        assertNull(filter.getSpecificOperatorDefault());

        assertNull(PredicateOperator.get("bad"));

        assertNotNull(filter.getIsNotOperators());
        assertNotNull(filter.getOperators());
    }

    @Test
    void testSetOperatorFromValue() {
        PredicateFilterFake filter = new PredicateFilterFake("eq1", PredicateOperator.EQUALS.getValue());
        filter.extract();
        assertEquals(PredicateOperator.EQUALS, filter.getOperator(0));
        assertFalse(filter.getIsNotOperator(0));

        filter = new PredicateFilterFake(null, "not_null");
        filter.extract();
        assertEquals(PredicateOperator.NULL, filter.getOperator(0));
        assertTrue(filter.getIsNotOperator(0));

        filter = new PredicateFilterFake(null, "null");
        filter.extract();
        assertEquals(PredicateOperator.NULL, filter.getOperator(0));
        assertFalse(filter.getIsNotOperator(0));

        filter = new PredicateFilterFake(null, PredicateOperator.NULL.getValue());
        filter.extract();
        assertEquals(PredicateOperator.NULL, filter.getOperator(0));

        filter = new PredicateFilterFake(null, PredicateOperator.BETWEEN.getValue());
        filter.specificOperator = true;
        filter.extract();
        assertEquals(PredicateOperator.BETWEEN, filter.getOperator(0));
    }

    @Test
    void testExtract() {
        PredicateFilterFake filter = new PredicateFilterFake(null, null);
        assertFalse(filter.extract());

        filter = new PredicateFilterFake(null, "not_");
        assertTrue(filter.extract());
        assertEquals(PredicateOperator.EQUALS, filter.getOperator(0));
        assertTrue(filter.getIsNotOperator(0));
        assertEquals("", filter.getValue(0));
    }

    @Test
    void getPredicateTest() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<EntityHelpers> query = builder.createQuery(EntityHelpers.class);
        final Root<EntityHelpers> root = query.from(EntityHelpers.class);

        PredicateFilterFake filter = new PredicateFilterFake("name", "test");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));

        filter = new PredicateFilterFake("name", "not_null");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));

        filter = new PredicateFilterFake("name", "null");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));

        filter = new PredicateFilterFake("name", "not_1");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));

        filter = new PredicateFilterFake("name", "1");
        assertTrue(filter.extract());
        assertNotNull(filter.getPredicate(builder, root, null));
    }

    @Test
    void getFieldValueTest() {
        final EntityTestSearch query = new EntityTestSearch();
        query.setName("test");

        assertNull(query.getFieldValue(null));

        Optional<Field> opt = Arrays.stream(EntityTestSearch.class.getDeclaredFields()).filter((f) -> {
            return f.getName().equals("name");
        }).map((f) -> {
            return f;
        }).findFirst();

        assertTrue(opt.isPresent());
        assertEquals("test", query.getFieldValue(opt.get()));

        opt = Arrays.stream(EntityTestSearch.class.getDeclaredFields()).filter((f) -> {
            return f.getName().equals("updateDate");
        }).map((f) -> {
            return f;
        }).findFirst();

        assertTrue(opt.isPresent());
        assertNull(query.getFieldValue(opt.get()));
    }

    private class PredicateFilterFake extends PredicateFilter {

        public boolean specificOperator = false;

        public PredicateFilterFake(final String name, final String value) {
            super(name, value, FilterType.Type.TEXT);
        }

        @Override
        public boolean isSpecificOperator() {
            return specificOperator;
        }

        @Override
        public PredicateOperator getSpecificOperator(int index) {
            return PredicateOperator.BETWEEN;
        }

        public boolean isSpecificOperatorDefault() {
            return super.isSpecificOperator();
        }

        public PredicateOperator getSpecificOperatorDefault() {
            return super.getSpecificOperator(0);
        }
    }
}
