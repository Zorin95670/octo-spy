package com.octo.model.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;

import com.octo.model.error.ErrorType;
import com.octo.model.exception.OctoException;

/**
 * Class to extract number filter from the query.
 *
 * @param <T>
 *            Type of expression.
 */
public class NumberQueryFilter<T> extends QueryFilter<T> {

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
     * @see Operator
     */
    private static final Pattern FILTER_PATTERN = Pattern.compile("(not){0,1}([0-9]*){1}(([,]{1}[0-9]+)*){0,1}");

    /**
     * Index of number list in regex groups.
     */
    private static final int GROUP_NUMBER_LIST = 3;

    /**
     * Is negation filter.
     */
    private boolean isNegation;
    /**
     * List of numbers to filter.
     */
    private final List<Long> numbers = new ArrayList<>();

    /**
     * Create number filter with field name and default type is "number".
     *
     * @param name
     *            Field name.
     * @param value
     *            Field value.
     */
    public NumberQueryFilter(final String name, final String value) {
        super(name, value, "number");
    }

    @Override
    public final boolean extract() {
        if (StringUtils.isBlank(this.getFieldValue())) {
            return false;
        }

        if ("null".equals(this.getFieldValue())) {
            return true;
        }

        final Matcher matcher = FILTER_PATTERN.matcher(this.getFieldValue());
        if (!matcher.matches()) {
            throw new OctoException(ErrorType.BAD_FILTER_VALUE, this.getFieldName(), this.getFieldValue());
        }

        this.isNegation = matcher.group(1) != null;
        try {
            this.numbers.add(Long.parseLong(matcher.group(2)));
            final String list = matcher.group(GROUP_NUMBER_LIST);
            Arrays.stream(list.split(",")).filter(StringUtils::isNotBlank).map(Long::parseLong)
                    .forEach(this.numbers::add);

        } catch (final NumberFormatException e) {
            throw new OctoException(e, ErrorType.BAD_FILTER_VALUE, this.getFieldName(), this.getFieldValue());
        }
        return true;
    }

    @Override
    public final Predicate getPredicate(final CriteriaBuilder builder, final Expression<T> field) {
        if ("null".equals(this.getFieldValue())) {
            return builder.isNull(field);
        }

        final Predicate in = field.in(this.numbers);

        if (this.isNegation) {
            return builder.not(in);
        }

        return in;
    }

}
