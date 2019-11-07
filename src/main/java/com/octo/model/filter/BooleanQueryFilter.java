package com.octo.model.filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/**
 * Class to extract boolean filter from the query.
 *
 * @param <T>
 *            Type of expression.
 */
public class BooleanQueryFilter<T> extends QueryFilter<T> {

    /**
     * Create boolean filter with field name and default type is "boolean".
     *
     * @param name
     *            Field name.
     * @param value
     *            Field value.
     */
    public BooleanQueryFilter(final String name, final String value) {
        super(name, value, "boolean");
    }

    @Override
    public final Predicate getPredicate(final CriteriaBuilder builder, final Expression<T> field) {
        return builder.equal(field, builder.literal(Boolean.parseBoolean(this.getFieldValue())));
    }

}
