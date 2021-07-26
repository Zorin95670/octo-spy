package com.octo.utils.predicate.filter;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Query with method to create filter.
 *
 * @author Vincent Moittié
 *
 */
public interface IQueryFilter {
    /**
     * Create filters from query.
     *
     * @param <T>
     *            Entity class.
     * @param entity
     *            Entity class of query.
     * @return List of filter.
     */
    <T> List<IPredicateFilter> getFilters(Class<T> entity);

    /**
     * Indicate if field name has specific filter.
     *
     * @param name
     *            Field name.
     * @return State.
     */
    boolean isSpecificFilter(String name);

    /**
     * Get specific filter for field.
     *
     * @param name
     *            Field name.
     * @param value
     *            Field value.
     * @return Specific filter.
     */
    IPredicateFilter getSpecificFilter(String name, String value);

    /**
     * Get value of field.
     *
     * @param field
     *            Field to get value.
     * @return Field's value or null.
     */
    default String getFieldValue(final Field field) {
        try {
            field.setAccessible(true);
            return (String) field.get(this);
        } catch (final Exception e) {
            return null;
        }
    }

}
