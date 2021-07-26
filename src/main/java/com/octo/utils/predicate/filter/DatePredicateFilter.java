package com.octo.utils.predicate.filter;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.utils.predicate.PredicateOperator;

/**
 * Class to extract date filter from the query.
 */
public class DatePredicateFilter extends PredicateFilter {
    /**
     * Regex pattern for date format.
     */
    private static final String FILTER_SUB_PATTERN = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
    /**
     * Regex to extract date.
     * <p>
     * This regex has 3 groups :
     * <ul>
     * <li>Date for between value, with format "yyyy-MM-dd" can be null.</li>
     * <li>Operator</li>
     * <li>Default date with format "yyy-MM-dd"</li>
     * </ul>
     * </p>
     *
     * @see PredicateOperator
     */
    private static final Pattern FILTER_PATTERN = Pattern
            .compile("(" + FILTER_SUB_PATTERN + ")?([a-zA-Z]{2})?(" + FILTER_SUB_PATTERN + "){1}");
    /**
     * Default date format for date filter.
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * Date format parser.
     */
    private final SimpleDateFormat parser = new SimpleDateFormat(DATE_FORMAT);
    /**
     * Position of sub-value in Regex group.
     */
    private static final int SUBVALUE_POSITION = 1;
    /**
     * Position of operator in Regex group.
     */
    private static final int OPERATOR_POSITION = 2;
    /**
     * Position of value in Regex group.
     */
    private static final int VALUE_POSITION = 3;
    /**
     * Sub value in case of 'between search'.
     */
    private String[] subValues;

    /**
     * Create date filter with field name and default type is "date".
     *
     * @param name
     *            Field name.
     * @param value
     *            Field value.
     */
    public DatePredicateFilter(final String name, final String value) {
        super(name, value, FilterType.Type.DATE);
        this.setSubValues(new String[this.getValues().length]);
        this.parser.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public final boolean isSpecificOperator() {
        return true;
    }

    @Override
    public final PredicateOperator getSpecificOperator(final int index) {
        String value = this.getValue(index);
        final Matcher matcher = FILTER_PATTERN.matcher(value);
        if (!matcher.find()) {
            throw new GlobalException(ErrorType.WRONG_FILTER_VALUE, this.getName(), value);
        }

        String operatorTextValue = matcher.group(OPERATOR_POSITION);

        if (operatorTextValue == null) {
            return PredicateOperator.EQUALS;
        }

        if (!PredicateOperator.isValid(operatorTextValue)) {
            throw new GlobalException(ErrorType.WRONG_FILTER_OPERATOR, this.getName(), operatorTextValue);
        }

        PredicateOperator operator = PredicateOperator.get(operatorTextValue);
        this.setValue(index, matcher.group(VALUE_POSITION));
        this.setSubValue(index, matcher.group(SUBVALUE_POSITION));

        if (PredicateOperator.BETWEEN.equals(operator) && this.subValues[index] == null) {
            throw new GlobalException(ErrorType.EMPTY_VALUE, this.getName());
        }
        return operator;
    }

    /**
     * Get sub value. Only for between operator.
     *
     * @param index
     *            Sub value index.
     * @return Sub value.
     */
    public String getSubValue(final int index) {
        return this.subValues[index];
    }

    /**
     * Set sub value.
     *
     * @param index
     *            Index of sub value to set.
     * @param value
     *            Value of sub value.
     */
    public void setSubValue(final int index, final String value) {
        this.subValues[index] = value;
    }

    /**
     * Get sub values. Only for between operator.
     *
     * @return Sub values.
     */
    public String[] getSubValues() {
        return Arrays.copyOf(this.subValues, this.subValues.length);
    }

    /**
     * Set sub value.
     *
     * @param subValues
     *            Sub values.
     */
    public void setSubValues(final String[] subValues) {
        this.subValues = Arrays.copyOf(subValues, subValues.length);
    }

    /**
     * Convert value to Date.
     *
     * @param value
     *            Date value.
     * @return Date or throw HarmonyRestException.
     */
    public Date getDate(final String value) {
        try {
            return this.parser.parse(value);
        } catch (final Exception e) {
            throw new GlobalException(e, ErrorType.WRONG_FILTER_VALUE, this.getName(), value);
        }
    }

    @Override
    public final <T> Predicate getPredicate(final int index, final CriteriaBuilder builder, final Expression<T> field) {
        final PredicateOperator operator = this.getOperators()[index];
        final String value = this.getValue(index);

        if (this.getIsNotOperator(index) && PredicateOperator.EQUALS.equals(operator)) {
            return builder.notEqual(field, this.getDate(value));
        } else if (PredicateOperator.EQUALS.equals(operator)) {
            return builder.equal(field, this.getDate(value));
        }

        Predicate predicate = null;
        if (PredicateOperator.INFERIOR.equals(operator)) {
            predicate = builder.lessThan(field.as(Date.class), this.getDate(value));
        } else if (PredicateOperator.SUPERIOR.equals(operator)) {
            predicate = builder.greaterThan(field.as(Date.class), this.getDate(value));
        } else {
            predicate = builder.between(field.as(Date.class), this.getDate(this.subValues[index]), this.getDate(value));
        }

        if (this.getIsNotOperator(index)) {
            return builder.not(predicate);
        }

        return predicate;
    }
}
