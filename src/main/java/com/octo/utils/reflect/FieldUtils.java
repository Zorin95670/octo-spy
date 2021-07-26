package com.octo.utils.reflect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Provide some helpful method to work with field.
 *
 * @author Vincent Moittié
 *
 */
public final class FieldUtils {

    /**
     * Do not use it.
     */
    private FieldUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Get declared fields, retrieve them from superclass too.
     *
     * @param root
     *            Class that have the field.
     * @param name
     *            Name of field to search.
     * @return Field or null.
     */
    public static Field getField(final Class<?> root, final String name) {
        Class<?> clazz = root;

        while (clazz != null) {
            final Optional<Field> opt = Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> field.getName().equals(name)).findFirst();
            if (opt.isPresent()) {
                return opt.get();
            }

            clazz = clazz.getSuperclass();
        }
        return null;
    }

    /**
     * Indicate if current class and all superclasses has the field.
     *
     * @param root
     *            Class that have the field.
     * @param name
     *            Name of field to search.
     * @return True if the field exist.
     */
    public static boolean hasField(final Class<?> root, final String name) {
        return getField(root, name) != null;
    }

    /**
     * Get all fields from class, private and inherited.
     *
     * @param clazz
     *            Class to retrieve fields from.
     * @return Fields
     */
    public static List<Field> getFields(final Class<?> clazz) {
        final List<Field> fields = new ArrayList<>();

        Class<?> current = clazz;
        while (current != null) {
            Arrays.stream(current.getDeclaredFields()).forEach(fields::add);
            current = current.getSuperclass();
        }

        return fields;
    }
}
