package com.octo.persistence.specification.filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/**
 * Class to extract boolean filter from the query.
 *
 * @author Vincent Moittié
 */
public class BooleanPredicateFilter extends PredicateFilter {

    /**
     * Create boolean filter with field name and default type is "boolean".
     *
     * @param name  Field name.
     * @param value Field value.
     */
    public BooleanPredicateFilter(final String name, final String value) {
        super(name, value, FilterType.Type.BOOLEAN);
    }

    @Override
    public final <T> Predicate getPredicate(final int index, final CriteriaBuilder builder, final Expression<T> field) {
        if (this.getIsNotOperator(index)) {
            return builder.notEqual(field, Boolean.parseBoolean(this.getValue(index)));
        }
        return builder.equal(field, Boolean.parseBoolean(this.getValue(index)));
    }

}
