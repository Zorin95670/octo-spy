package com.octo.persistence.specification.filter;

import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.persistence.specification.PredicateOperator;

import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.util.Arrays;

/**
 * Class to extract number filter from the query.
 *
 * @author Vincent Moittié
 */
public class NumberPredicateFilter extends PredicateFilter {

    /**
     * Create number filter with field name and default type is "number".
     *
     * @param name  Field name.
     * @param value Field value.
     */
    public NumberPredicateFilter(final String name, final String value) {
        super(name, value, FilterType.Type.NUMBER);
    }

    @Override
    public final void setOperatorFromValue(final int index) {
        super.setOperatorFromValue(index);
        if (PredicateOperator.EQUALS.equals(this.getOperator(index))) {
            try {
                Long.parseLong(this.getValue(index));
            } catch (NumberFormatException e) {
                throw new GlobalException(e, ErrorType.WRONG_FILTER_VALUE, this.getName(), this.getValue(index));
            }
        }
    }

    @Override
    public final <T, Y> Predicate getPredicate(final CriteriaBuilder builder, final From<T, Y> root,
                                               final CommonAbstractCriteria query) {
        int allNotEquals = 0;
        int allEquals = 0;

        for (int index = 0; index < this.getValues().length; index++) {
            if (!PredicateOperator.EQUALS.equals(this.getOperator(index))) {
                break;
            }
            if (this.getIsNotOperator(index)) {
                allNotEquals += 1;
            } else {
                allEquals += 1;
            }
        }

        if (this.getValues().length == allNotEquals) {
            return builder
                    .not(root.get(this.getName()).in(Arrays.stream(this.getValues()).map(Long::parseLong).toList()));
        }
        if (this.getValues().length == allEquals) {
            return root.get(this.getName()).in(Arrays.stream(this.getValues()).map(Long::parseLong).toList());
        }
        Predicate[] predicates = new Predicate[this.getValues().length];
        for (int index = 0; index < getValues().length; index++) {
            predicates[index] = this.getPredicate(index, builder, root);
        }
        return builder.and(builder.or(predicates));
    }
}
