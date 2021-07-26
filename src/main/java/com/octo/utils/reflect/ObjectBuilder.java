package com.octo.utils.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;

/**
 * Build object and set property on it.
 *
 * @param <T>
 *            Object.
 */
public final class ObjectBuilder<T> {

    /**
     * object to build.
     */
    private final T object;

    /**
     * Create instance of <T>.
     *
     * @param clazz
     *            Class to instantiate.
     * @param parameterTypes
     *            Parameters types to call selected constructor.
     * @param initargs
     *            Parameters values to selected constructor.
     */
    private ObjectBuilder(final Class<T> clazz, final Class<?>[] parameterTypes, final Object[] initargs) {
        this.object = this.create(clazz, parameterTypes, initargs);
    }

    /**
     * Initialize builder.
     *
     * @param clazz
     *            Class to instantiate.
     * @param <T>
     *            Object type.
     * @return Builder.
     */
    public static <T> ObjectBuilder<T> init(final Class<T> clazz) {
        return init(clazz, null, null);
    }

    /**
     * Initialize builder.
     *
     * @param clazz
     *            Class to instantiate.
     * @param parameterTypes
     *            Parameters types to call selected constructor.
     * @param initargs
     *            Parameters values to selected constructor.
     * @param <T>
     *            Object type.
     * @return Builder.
     */
    public static <T> ObjectBuilder<T> init(final Class<T> clazz, final Class<?>[] parameterTypes,
            final Object[] initargs) {
        return new ObjectBuilder<>(clazz, parameterTypes, initargs);
    }

    /**
     * Get the builded object.
     *
     * @return Instance of T.
     */
    public T build() {
        return this.object;
    }

    /**
     * Create new instance of <T>.
     *
     * @param clazz
     *            Class to instantiate.
     * @param parameterTypes
     *            Parameters types to call selected constructor.
     * @param initargs
     *            Parameters values to selected constructor.
     * @return Instance of T.
     */
    public T create(final Class<T> clazz, final Class<?>[] parameterTypes, final Object[] initargs) {
        try {
            return clazz.getConstructor(parameterTypes).newInstance(initargs);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            throw new GlobalException(e, ErrorType.INTERNAL_ERROR, "constructor");
        }
    }

    /**
     * Set field of entity.
     *
     * @param key
     *            Name of field to set.
     * @param value
     *            Value of field.
     * @return Builder.
     */
    public ObjectBuilder<T> setField(final String key, final Object value) {
        final Field field = FieldUtils.getField(this.object.getClass(), key);

        if (field == null) {
            throw new GlobalException(ErrorType.INTERNAL_ERROR, "field", key);
        }

        try {
            field.setAccessible(true);
            field.set(this.object, value);
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new GlobalException(e, ErrorType.INTERNAL_ERROR, "field", key);
        }

        return this;
    }
}
