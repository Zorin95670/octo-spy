package com.octo.utils.predicate;

import java.util.Arrays;
import java.util.Optional;

import com.octo.utils.predicate.filter.IPredicateFilter;

/**
 * List of available operator for QueryFilter.
 *
 * @see IPredicateFilter
 *
 * @author Vincent Moittié
 *
 */
public enum PredicateOperator {
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
    BETWEEN("bt"),
    /**
     * Operator null, value "null".
     */
    NULL("null"),
    /**
     * Operator like, value "lk".
     */
    LIKE("lk");

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
    PredicateOperator(final String value) {
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
        return Arrays.stream(PredicateOperator.values()).anyMatch(o -> o.getValue().equalsIgnoreCase(operator));
    }

    /**
     * Get operator from value.
     *
     * @param operator
     *            Operator value.
     * @return Operator or null.
     */
    public static PredicateOperator get(final String operator) {
        Optional<PredicateOperator> opt = Arrays.stream(PredicateOperator.values())
                .filter(o -> o.getValue().equalsIgnoreCase(operator)).findAny();
        if (opt.isPresent()) {
            return opt.get();
        }
        return null;
    }
}
