package com.octo.utils.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;

import com.octo.model.error.ErrorType;
import com.octo.model.exception.OctoException;

/**
 * Copy all properties of T in Y. Make simple conversion.
 *
 * @param <T>
 *            Source.
 * @param <Y>
 *            Destination.
 */
public class BeanMapper<T, Y> implements Function<T, Y> {

    /**
     * Object to return.
     */
    private final Class<Y> clazz;

    /**
     * Array of property names to ignore when conversion.
     */
    private final String[] ignoreFields;

    /**
     * Default constructor.
     *
     * @param clazz
     *            Class of object to return.
     */
    public BeanMapper(final Class<Y> clazz) {
        this(clazz, new String[0]);
    }

    /**
     * Default constructor.
     *
     *
     * @param clazz
     *            Class of object to return.
     * @param ignoreFields
     *            Array of property names to ignore when conversion. Can be null.
     */
    public BeanMapper(final Class<Y> clazz, final String... ignoreFields) {
        this.clazz = clazz;
        this.ignoreFields = ignoreFields;
    }

    @Override
    public final Y apply(final T object) {
        final Y result = this.init();
        final List<Field> fieldsSource = this.getFields(object);
        final List<Field> fieldsDest = this.getFields(result);

        final List<String> listOfIgnoredFields = Arrays.asList(this.ignoreFields);

        fieldsDest.stream().forEach(fieldDest -> {
            final Optional<Field> opt = fieldsSource.stream()
                    // Take only not synthetic and not ignored field, map source in destination field
                    .filter(fieldSource -> !fieldSource.isSynthetic()
                            && !listOfIgnoredFields.contains(fieldSource.getName())
                            && fieldSource.getName().equals(fieldDest.getName()))
                    .findFirst();

            if (opt.isPresent()) {
                this.setFieldValue(opt.get(), fieldDest, result, object);
            }
        });

        return result;
    }

    /**
     * Set destination field value from source field and object.
     *
     * @param source
     *            Field source.
     * @param destination
     *            Field destination.
     * @param result
     *            Object to set value.
     * @param object
     *            Object to get value.
     */
    public void setFieldValue(final Field source, final Field destination, final Object result, final Object object) {
        try {
            source.setAccessible(true);
            final Object value = source.get(object);

            if (value == null) {
                destination.setAccessible(true);
                destination.set(result, null);
            } else {
                BeanUtils.setProperty(result, source.getName(), value);
            }
        } catch (ConversionException | IllegalAccessException | InvocationTargetException
                | IllegalArgumentException e) {
            throw new OctoException(e, ErrorType.INTERNAL_ERROR, source.getName(), null);
        }
    }

    /**
     * Get all fields from class, private and inherited.
     *
     * @param object
     *            Object to retrieve fields.
     * @return Fields
     */
    public List<Field> getFields(final Object object) {
        final List<Field> fields = new ArrayList<>();

        Class<?> current = object.getClass();
        while (current != null) {
            Arrays.stream(current.getDeclaredFields()).forEach(fields::add);
            current = current.getSuperclass();
        }

        return fields;
    }

    /**
     * Initialize clazz to return.
     *
     * @return Object.
     */
    public Y init() {
        try {
            return this.clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            throw new OctoException(e, ErrorType.INTERNAL_ERROR, this.clazz.getName(), null);
        }
    }

}
