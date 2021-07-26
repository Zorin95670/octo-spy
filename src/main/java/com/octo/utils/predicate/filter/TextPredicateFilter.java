package com.octo.utils.predicate.filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;

import com.octo.utils.predicate.PredicateOperator;

/**
 * Class to extract text filter from the query.
 */
public class TextPredicateFilter extends PredicateFilter {

    /**
     * Create text filter with field name and default type filter as "text".
     *
     * @param name
     *            Field name.
     * @param value
     *            Field value.
     */
    public TextPredicateFilter(final String name, final String value) {
        super(name, value, FilterType.Type.TEXT);
    }

    @Override
    public final boolean isSpecificOperator() {
        return true;
    }

    @Override
    public final PredicateOperator getSpecificOperator(final int index) {
        String value = this.getValue(index);
        if (StringUtils.startsWithIgnoreCase(value, PredicateOperator.LIKE.getValue())) {
            this.setValue(index, value.substring(PredicateOperator.LIKE.getValue().length()));
            return PredicateOperator.LIKE;
        }
        return PredicateOperator.EQUALS;
    }

    @Override
    public final <T> Predicate getPredicate(final int index, final CriteriaBuilder builder, final Expression<T> field) {
        String value = this.getValue(index);
        boolean isNot = this.getIsNotOperator(index);

        if (PredicateOperator.LIKE.equals(this.getOperator(index))) {
            if (isNot) {
                return builder.notLike(builder.upper((Expression<String>) field), StringUtils.upperCase(value));
            }
            return builder.like(builder.upper((Expression<String>) field), StringUtils.upperCase(value));
        } else if (isNot) {
            return builder.notEqual(field, value);
        }
        return builder.equal(field, value);
    }
}
