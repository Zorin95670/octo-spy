package com.octo.persistence.specification.filter;

import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.persistence.specification.PredicateOperator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to extract date filter from the query.
 *
 * @author Vincent Moittié
 */
public class DatePredicateFilter extends PredicateFilter {
    /**
     * Default date format for date filter.
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
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
            .compile("(" + FILTER_SUB_PATTERN + ")?([a-zA-Z]{2})?(" + FILTER_SUB_PATTERN + ")");
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
     * Date format parser.
     */
    private final SimpleDateFormat parser = new SimpleDateFormat(DATE_FORMAT);
    /**
     * Sub value in case of 'between search'.
     */
    private String[] subValues;

    /**
     * Create date filter with field name and default type is "date".
     *
     * @param name  Field name.
     * @param value Field value.
     */
    public DatePredicateFilter(final String name, final String value) {
        super(name, value, FilterType.Type.DATE);
        this.setSubValues(new String[this.getValues().length]);
        this.parser.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /**
     * Set sub value.
     *
     * @param index Index of sub value to set.
     * @param value Value of sub value.
     */
    public void setSubValue(final int index, final String value) {
        this.subValues[index] = value;
    }

    /**
     * Set sub value.
     *
     * @param subValues Sub values.
     */
    public void setSubValues(final String[] subValues) {
        this.subValues = Arrays.copyOf(subValues, subValues.length);
    }

    /**
     * Convert value to Date.
     *
     * @param value Date value.
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
    public final boolean extract() {
        if (this.getValues().length == 0) {
            return false;
        }

        for (int index = 0; index < this.getValues().length; index++) {
            this.setOperatorFromValue(index);
            if (PredicateOperator.NULL.equals(this.getOperator(index))) {
                continue;
            }
            String value = this.getValue(index);
            final Matcher matcher = FILTER_PATTERN.matcher(value);
            if (!matcher.find()) {
                throw new GlobalException(ErrorType.WRONG_FILTER_VALUE, this.getName(), value);
            }

            String operator = matcher.group(OPERATOR_POSITION);
            if (operator != null) {
                this.setOperator(index, PredicateOperator.get(operator).orElseThrow(() ->
                        new GlobalException(ErrorType.WRONG_FILTER_OPERATOR, this.getName(), operator)));
            }
            this.setValue(index, matcher.group(VALUE_POSITION));
            this.setSubValue(index, matcher.group(SUBVALUE_POSITION));

            if (PredicateOperator.BETWEEN.equals(this.getOperator(index))
                    && this.subValues[index] == null) {
                throw new GlobalException(ErrorType.EMPTY_VALUE, this.getName());
            }
        }
        return true;
    }

    @Override
    public final <T> Predicate getPredicate(final int index, final CriteriaBuilder builder, final Expression<T> field) {
        Predicate predicate;
        if (PredicateOperator.EQUALS.equals(this.getOperator(index))) {
            predicate = builder.equal(field, this.getDate(this.getValue(index)));
        } else if (PredicateOperator.INFERIOR.equals(this.getOperator(index))) {
            predicate = builder.lessThan(field.as(Date.class), this.getDate(this.getValue(index)));
        } else if (PredicateOperator.SUPERIOR.equals(this.getOperator(index))) {
            predicate = builder.greaterThan(field.as(Date.class), this.getDate(this.getValue(index)));
        } else {
            predicate = builder.between(field.as(Date.class), this.getDate(this.subValues[index]),
                    this.getDate(this.getValue(index)));
        }

        if (this.getIsNotOperator(index)) {
            return builder.not(predicate);
        }
        return predicate;
    }
}
