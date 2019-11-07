package com.octo.model.filter;

import java.util.Arrays;

/**
 * List of available operator for QueryFilter.
 *
 * @author vmoittie
 * @see IQueryFilter
 *
 */
public enum Operator {
    /**
     * Operator equals, value "=".
     */
    EQUALS("eq"),
    /**
     * Operator inferior, value "<".
     */
    INFERIOR("lt"),
    /**
     * Operator superior, value ">".
     */
    SUPERIOR("gt"),
    /**
     * Operator between, value "<>".
     */
    BETWEEN("bt");

    /**
     * String representation of operator.
     */
    private String value;

    /**
     * Default constructor.
     *
     * @param value
     *            Representation of operator
     */
    Operator(final String value) {
        this.value = value;
    }

    /**
     * Get string representation of operator.
     *
     * @return Operator.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Indicates if operator is valid or not.
     *
     * @param operator
     *            Operator value.
     * @return If operator exists return true otherwise false.
     */
    public static boolean isValid(final String operator) {
        return Arrays.stream(Operator.values()).anyMatch(o -> o.getValue().equalsIgnoreCase(operator));
    }
}
