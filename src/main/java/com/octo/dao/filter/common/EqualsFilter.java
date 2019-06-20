package com.octo.dao.filter.common;

import java.util.function.BiFunction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Create equality filter.
 *
 * @author vmoittie
 *
 * @param <T>
 *            Entity.
 */
public class EqualsFilter<T> implements BiFunction<CriteriaBuilder, Root<T>, Predicate> {

    /**
     * Field's name.
     */
    private String field;
    /**
     * Value to compare.
     */
    private Object value;

    /**
     * Add equality filter.
     *
     * @param field
     *            Field's name to apply filter.
     * @param value
     *            Value's to be equal.
     */
    public EqualsFilter(final String field, final Object value) {
        this.field = field;
        this.value = value;
    }

    @Override
    public final Predicate apply(final CriteriaBuilder builder, final Root<T> root) {
        return builder.equal(root.get(this.field), this.value);
    }

}
