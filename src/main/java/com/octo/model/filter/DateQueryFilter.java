package com.octo.model.filter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import com.octo.model.error.ErrorType;
import com.octo.model.exception.OctoException;

/**
 * Class to extract date filter from the query.
 */
public class DateQueryFilter extends QueryFilter<Date> {

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
     * @see Operator
     */
    private static final Pattern FILTER_PATTERN = Pattern.compile(
            "(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})?([a-zA-Z]{2})?(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}){1}");
    /**
     * Date format parser.
     */
    private final SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
     * Type of search.
     */
    private String operator;
    /**
     * Sub value in case of 'between search'.
     */
    private String subValue;

    /**
     * Create date filter with field name and default type is "date".
     *
     * @param name
     *            Field name.
     * @param value
     *            Field value.
     */
    public DateQueryFilter(final String name, final String value) {
        super(name, value, "date");
        this.parser.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public final boolean extract() {
        if (this.getFieldValue() == null) {
            return false;
        }

        final Matcher matcher = FILTER_PATTERN.matcher(this.getFieldValue());
        if (!matcher.find()) {
            throw new OctoException(ErrorType.BAD_FILTER_VALUE, this.getFieldName(), this.getFieldValue());
        }

        this.setOperator(matcher.group(OPERATOR_POSITION));
        this.setValue(matcher.group(VALUE_POSITION));
        this.setSubValue(matcher.group(SUBVALUE_POSITION));

        if (Operator.BETWEEN.getValue().equals(this.operator) && this.getSubValue() == null) {
            throw new OctoException(ErrorType.EMPTY_VALUE, this.getFieldName(), null);
        }
        return true;
    }

    /**
     * Get operator value.
     *
     * @return Operator value.
     */
    public String getOperator() {
        return this.operator;
    }

    /**
     * Set operator value. If operator is null Equals is set.
     *
     * @param operator
     *            Operator value.
     * @throws HarmonyRestException
     *             on bad operator.
     * @see Operator
     */
    public void setOperator(final String operator) {
        if (operator == null) {
            this.operator = Operator.EQUALS.getValue();
            return;
        }

        if (!Operator.isValid(operator)) {
            throw new OctoException(ErrorType.BAD_FILTER_OPERATOR, this.getFieldName(), operator);
        }

        this.operator = operator;
    }

    /**
     * Get sub value. Only for between operator.
     *
     * @return Sub value.
     */
    public String getSubValue() {
        return this.subValue;
    }

    /**
     * Set sub value.
     *
     * @param subValue
     *            Sub value.
     */
    public void setSubValue(final String subValue) {
        this.subValue = subValue;
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
            throw new OctoException(e, ErrorType.BAD_FILTER_VALUE, this.getFieldName(), value);
        }
    }

    @Override
    public final Predicate getPredicate(final CriteriaBuilder builder, final Expression<Date> field) {
        if (Operator.EQUALS.getValue().equals(this.operator)) {
            return builder.equal(field, this.getDate(this.getValue()));
        }
        if (Operator.INFERIOR.getValue().equals(this.operator)) {
            return builder.lessThan(field, this.getDate(this.getValue()));
        }
        if (Operator.SUPERIOR.getValue().equals(this.operator)) {
            return builder.greaterThan(field, this.getDate(this.getValue()));
        }
        return builder.between(field, this.getDate(this.getSubValue()), this.getDate(this.getValue()));
    }
}
