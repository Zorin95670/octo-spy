package com.octo.utils.predicate.filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/**
 * Class to extract token filter from the query.
 *
 * @author Vincent Moittié
 *
 */
public class TokenPredicateFilter extends PredicateFilter {

    /**
     * Create token filter with field name and default type filter as "token".
     *
     * @param name
     *            Field name.
     * @param value
     *            Field value.
     */
    public TokenPredicateFilter(final String name, final String value) {
        super(name, value, FilterType.Type.TOKEN);
    }

    @Override
    public final <T> Predicate getPredicate(final int index, final CriteriaBuilder builder, final Expression<T> field) {
        String value = this.getValue(index);
        boolean isNot = this.getIsNotOperator(index);

        if (isNot) {
            return builder.notEqual(field, builder.function("crypt", String.class, builder.literal(value), field));
        }

        return builder.equal(field, builder.function("crypt", String.class, builder.literal(value), field));
    }
}
