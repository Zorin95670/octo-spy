package com.octo.utils.predicate.filter;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.utils.predicate.PredicateOperator;

/**
 * Class to extract number filter from the query.
 */
public class NumberPredicateFilter extends PredicateFilter {

    /**
     * Regex to extract number.
     * <p>
     * This regex has 2 groups :
     * <ul>
     * <li>The negation group (not)</li>
     * <li>List of number</li>
     * </ul>
     * </p>
     *
     * @see PredicateOperator
     */
    private static final Pattern FILTER_PATTERN = Pattern
            .compile("(" + NOT_DELIMITER + ")?((?:\\d+" + OR_DELIMITER + "\\s*)*\\d+)");

    /**
     * Create number filter with field name and default type is "number".
     *
     * @param name
     *            Field name.
     * @param value
     *            Field value.
     */
    public NumberPredicateFilter(final String name, final String value) {
        super(name, value, FilterType.Type.NUMBER);
    }

    @Override
    public final boolean isSpecificOperator() {
        return true;
    }

    @Override
    public final PredicateOperator getSpecificOperator(final int index) {
        final String value = this.getValue(index);
        final Matcher matcher = FILTER_PATTERN.matcher(value);
        if (!matcher.matches()) {
            throw new GlobalException(ErrorType.WRONG_FILTER_VALUE, this.getName(), value);
        }

        try {
            Long.parseLong(value);
        } catch (final NumberFormatException e) {
            throw new GlobalException(e, ErrorType.WRONG_FILTER_VALUE, this.getName(), value);
        }

        return PredicateOperator.EQUALS;
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
            return builder.not(root.get(this.getName())
                    .in(Arrays.stream(this.getValues()).map(Long::parseLong).collect(Collectors.toList())));
        }
        if (this.getValues().length == allEquals) {
            return root.get(this.getName())
                    .in(Arrays.stream(this.getValues()).map(Long::parseLong).collect(Collectors.toList()));
        }
        Predicate[] predicates = new Predicate[this.getValues().length];
        for (int index = 0; index < getValues().length; index++) {
            predicates[index] = this.getPredicate(index, builder, root);
        }
        return builder.and(builder.or(predicates));
    }
}
