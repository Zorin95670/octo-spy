package com.octo.model.filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Abstract class to implements default field for QueryFilter.
 *
 * @param <T>
 *            Type of expression.
 */
public abstract class QueryFilter<T> implements IQueryFilter<T> {

    /**
     * Type of filter.
     */
    private String type;
    /**
     * Value of filter.
     */
    private String value;
    /**
     * Name of field to apply filter.
     */
    private String fieldName;
    /**
     * Value of field to apply filter.
     */
    private String fieldValue;

    /**
     * Create filter with field name.
     *
     * @param name
     *            Field name to apply filter.
     * @param value
     *            Field value to apply filter.
     * @param type
     *            Type of filter.
     */
    public QueryFilter(final String name, final String value, final String type) {
        this.setFieldName(name);
        this.setFieldValue(value);
        this.setType(type);
    }

    /**
     * Type of filter.
     *
     * @return Type of filter.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Set type of filter.
     *
     * @param type
     *            Type of filter.
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * Get value of filter.
     *
     * @return Value.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Set value of filter.
     *
     * @param value
     *            Value.
     */
    public void setValue(final String value) {
        this.value = value;
    }

    /**
     * Get field name.
     *
     * @return Field name.
     */
    public String getFieldName() {
        return this.fieldName;
    }

    /**
     * Set field name.
     *
     * @param name
     *            Field name.
     */
    public void setFieldName(final String name) {
        this.fieldName = name;
    }

    /**
     * Get field value.
     *
     * @return Field value.
     */
    public String getFieldValue() {
        return this.fieldValue;
    }

    /**
     * Set field value.
     *
     * @param text
     *            Field value.
     */
    public void setFieldValue(final String text) {
        this.fieldValue = text;
    }

    /**
     * If field value is null return false, otherwise set field value in value and return true.
     */
    @Override
    public boolean extract() {
        if (this.getFieldValue() == null) {
            return false;
        }

        this.setValue(this.getFieldValue());
        return true;
    }

    /**
     * Return default predicate. This method call getPredicate with Expression. Override it, to use your wanted
     * expression.
     */
    @Override
    public <Y> Predicate getPredicate(final CriteriaBuilder builder, final Root<Y> root) {
        return this.getPredicate(builder, root.get(this.getFieldName()));
    }
}
