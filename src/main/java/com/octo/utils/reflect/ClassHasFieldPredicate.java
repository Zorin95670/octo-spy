package com.octo.utils.reflect;

import java.util.function.Predicate;

/**
 * Check if class contains this field name.
 *
 * @author vmoittie
 *
 */
public class ClassHasFieldPredicate implements Predicate<String> {

    /**
     * Class to test.
     */
    private final Class<?> rootClazz;

    /**
     * Default constructor.
     *
     * @param clazz
     *            Class to check field.
     */
    public ClassHasFieldPredicate(final Class<?> clazz) {
        this.rootClazz = clazz;
    }

    @Override
    public final boolean test(final String fieldName) {
        return FieldUtils.hasField(this.rootClazz, fieldName);
    }

}
