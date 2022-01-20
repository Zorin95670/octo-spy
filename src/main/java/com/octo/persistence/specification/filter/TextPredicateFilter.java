package com.octo.persistence.specification.filter;

import com.octo.persistence.specification.PredicateOperator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/**
 * Class to extract text filter from the query.
 *
 * @author Vincent Moittié
 */
public class TextPredicateFilter extends PredicateFilter {

    /**
     * Create text filter with field name and default type filter as "text".
     *
     * @param name  Field name.
     * @param value Field value.
     */
    public TextPredicateFilter(final String name, final String value) {
        super(name, value, FilterType.Type.TEXT);
    }

    @Override
    public final void setOperatorFromValue(final int index) {
        super.setOperatorFromValue(index);
        String value = getValue(index);
        if (value.toLowerCase().startsWith(PredicateOperator.LIKE.getValue())) {
            value = value.toUpperCase().replace("*", "%");
            this.setOperator(index, PredicateOperator.LIKE);
            this.setValue(index, value.substring(PredicateOperator.LIKE.getValue().length()));
        }
    }

    @Override
    public final <T> Predicate getPredicate(final int index, final CriteriaBuilder builder, final Expression<T> field) {
        if (PredicateOperator.LIKE.equals(this.getOperator(index))) {
            String value = this.getValue(index);
            if (this.getIsNotOperator(index)) {
                return builder.notLike(builder.upper((Expression<String>) field), value);
            }
            return builder.like(builder.upper((Expression<String>) field), value);
        }
        return super.getPredicate(index, builder, field);
    }
}
