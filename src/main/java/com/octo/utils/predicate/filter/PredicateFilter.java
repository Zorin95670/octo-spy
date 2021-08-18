package com.octo.utils.predicate.filter;

import java.util.Arrays;

import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import com.octo.utils.predicate.PredicateOperator;

/**
 * Abstract class to implements default field for QueryFilter.
 *
 * @author Vincent Moittié
 *
 */
public abstract class PredicateFilter implements IPredicateFilter {

    /**
     * Or delimiter.
     */
    protected static final String OR_DELIMITER = "\\|";

    /**
     * Not delimiter.
     */
    protected static final String NOT_DELIMITER = "not_";
    /**
     * Operator of this filter.
     */
    private PredicateOperator[] operators;
    /**
     * Indicate if it's not operator.
     */
    private boolean[] isNotOperators;
    /**
     * Name of field to apply filter.
     */
    private String name;
    /**
     * Values of filter.
     */
    private String[] values;
    /**
     * Type of filter.
     */
    private String type;

    /**
     * Create filter with field name.
     *
     * @param name
     *            Field name to apply filter.
     * @param value
     *            Text to convert in filters.
     * @param type
     *            Type of filter.
     */
    public PredicateFilter(final String name, final String value, final FilterType.Type type) {
        this.setName(name);
        this.setValues(value);
        this.setType(type.name());
        this.setOperators(new PredicateOperator[this.getValues().length]);
        this.setIsNotOperators(new boolean[this.getValues().length]);
    }

    /**
     * Get field name.
     *
     * @return Field name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set field name.
     *
     * @param name
     *            Field name.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Get is not operator state.
     *
     * @param index
     *            Value index.
     * @return State.
     */
    public boolean getIsNotOperator(final int index) {
        return this.isNotOperators[index];
    }

    /**
     * Set is not operator state on index.
     *
     * @param index
     *            Index of value to set.
     * @param state
     *            State.
     */
    public void setIsNotOperator(final int index, final boolean state) {
        this.isNotOperators[index] = state;
    }

    /**
     * Get is not operator states.
     *
     * @return States.
     */
    public boolean[] getIsNotOperators() {
        return Arrays.copyOf(this.isNotOperators, this.isNotOperators.length);
    }

    /**
     * Set is not operator states.
     *
     * @param states
     *            States list.
     */
    public final void setIsNotOperators(final boolean[] states) {
        this.isNotOperators = Arrays.copyOf(states, states.length);
    }

    /**
     * Get value of filter from index.
     *
     * @param index
     *            Value index.
     * @return Value.
     */
    public String getValue(final int index) {
        return this.values[index];
    }

    /**
     * Set value on index.
     *
     * @param index
     *            Index of value to set.
     * @param value
     *            Value.
     */
    public void setValue(final int index, final String value) {
        this.values[index] = value;
    }

    /**
     * Get values of filter.
     *
     * @return Values.
     */
    public String[] getValues() {
        return Arrays.copyOf(this.values, this.values.length);
    }

    /**
     * Set values of filter.
     *
     * @param value
     *            Unsplit value.
     */
    public void setValues(final String value) {
        if (value != null) {
            this.values = value.split(OR_DELIMITER);
        } else {
            this.values = new String[0];
        }
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
     * Get operator.
     *
     * @param index
     *            Operator index.
     * @return Operator.
     */
    public final PredicateOperator getOperator(final int index) {
        return operators[index];
    }

    /**
     * Set operator on index.
     *
     * @param index
     *            Operator index.
     * @param operator
     *            Operator to set.
     */
    public final void setOperator(final int index, final PredicateOperator operator) {
        this.operators[index] = operator;
    }

    /**
     * Get operators.
     *
     * @return Operator.
     */
    public final PredicateOperator[] getOperators() {
        return Arrays.copyOf(this.operators, this.operators.length);
    }

    /**
     * Set operators.
     *
     * @param operators
     *            Operator list.
     */
    public final void setOperators(final PredicateOperator[] operators) {
        this.operators = Arrays.copyOf(operators, operators.length);
    }

    /**
     * Set operator from value.
     *
     * @param index
     *            Value index.
     */
    public final void setOperatorFromValue(final int index) {
        this.setOperator(index, this.getDefaultOperator());
        if (this.values[index].toLowerCase().startsWith(NOT_DELIMITER)) {
            this.setIsNotOperator(index, true);
            this.setValue(index, this.values[index].substring(NOT_DELIMITER.length()));
        }
        if (PredicateOperator.NULL.getValue().equalsIgnoreCase(this.values[index])) {
            this.setOperator(index, PredicateOperator.NULL);
            return;
        }
        if (isSpecificOperator()) {
            this.setOperator(index, this.getSpecificOperator(index));
        }
    }

    /**
     * If field value is null return false, otherwise set field value in value
     * and return true.
     */
    @Override
    public boolean extract() {
        if (this.getValues().length == 0) {
            return false;
        }

        for (int index = 0; index < this.getValues().length; index++) {
            this.setOperatorFromValue(index);
        }

        return true;
    }

    /**
     * Override this if you need to add predicate on sub-query.
     */
    @Override
    public <T, Y> Predicate getPredicate(final CriteriaBuilder builder, final From<T, Y> root,
            final CommonAbstractCriteria query) {
        Predicate[] predicates = new Predicate[this.getValues().length];
        for (int index = 0; index < this.getValues().length; index++) {
            predicates[index] = this.getPredicate(index, builder, root);
        }
        return builder.and(builder.or(predicates));
    }

    /**
     * Return default predicate. This method call getPredicate with Expression.
     * Override it, to use your wanted expression.
     */
    @Override
    public <T, Y> Predicate getPredicate(final int index, final CriteriaBuilder builder, final From<T, Y> root) {
        if (PredicateOperator.NULL.equals(this.operators[index])) {
            return this.getNullPredicate(index, builder, root.get(this.getName()));
        }

        return this.getPredicate(index, builder, root.get(this.getName()));
    }

    /**
     * Indicate if your predicate filter is specific. Used with
     * 'getSpecificOperator'.
     */
    @Override
    public boolean isSpecificOperator() {
        return false;
    }

    /**
     * Used to return specific predicate. Used with 'isSpecificOperator'.
     */
    @Override
    public PredicateOperator getSpecificOperator(final int index) {
        return null;
    }

    @Override
    public final PredicateOperator getDefaultOperator() {
        return PredicateOperator.EQUALS;
    }

    /**
     * Override to return specific null predicate.
     */
    @Override
    public <T> Predicate getNullPredicate(final int index, final CriteriaBuilder builder, final Expression<T> field) {
        if (this.getIsNotOperator(index)) {
            return builder.isNotNull(field);
        }
        return builder.isNull(field);
    }

    /**
     * Override to return specific predicate.
     */
    @Override
    public <T> Predicate getPredicate(final int index, final CriteriaBuilder builder, final Expression<T> field) {
        if (this.getIsNotOperator(index)) {
            return builder.notEqual(field, this.getValue(index));
        }
        return builder.equal(field, this.getValue(index));
    }
}
