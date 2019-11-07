package com.octo.model.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.BadRequestException;

import org.junit.Test;

public class QueryFilterTest {
    @Test
    public void test() {
        final QueryFilterFake queryFilter = new QueryFilterFake("test", "value");

        assertEquals("test", queryFilter.getFieldName());
        assertEquals("value", queryFilter.getFieldValue());

        assertNull(queryFilter.getType());
        queryFilter.setType("type");
        assertEquals("type", queryFilter.getType());

        assertNull(queryFilter.getValue());
        queryFilter.setValue("value");
        assertEquals("value", queryFilter.getValue());
    }

    private class QueryFilterFake extends QueryFilter<String> {
        public QueryFilterFake(final String name, final String value) {
            super(name, value, null);
        }

        @Override
        public boolean extract() throws BadRequestException {
            return true;
        }

        @Override
        public <T> Predicate getPredicate(final CriteriaBuilder builder, final Root<T> root) {
            return null;
        }

        @Override
        public Predicate getPredicate(final CriteriaBuilder builder, final Expression<String> field) {
            return null;
        }

    }
}
