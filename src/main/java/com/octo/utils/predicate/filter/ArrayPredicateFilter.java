package com.octo.utils.predicate.filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/**
 * Class to extract array filter from the query.
 *
 * @author Vincent Moittié
 *
 */
public class ArrayPredicateFilter extends PredicateFilter {

    /**
     * Create array filter with field name and default type is "array".
     *
     * @param name
     *            Field name.
     * @param value
     *            Field value.
     */
    public ArrayPredicateFilter(final String name, final String value) {
        super(name, value, FilterType.Type.ARRAY);
    }

    @Override
    public final <T> Predicate getPredicate(final int index, final CriteriaBuilder builder, final Expression<T> field) {
        if (this.getIsNotOperator(index)) {
            return builder.notEqual(builder.literal(this.getValue(index)),
                    builder.function("any", String.class, field));
        }
        return builder.equal(builder.literal(this.getValue(index)), builder.function("any", String.class, field));
    }
}
