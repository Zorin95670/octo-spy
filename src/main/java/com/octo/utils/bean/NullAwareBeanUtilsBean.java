package com.octo.utils.bean;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Merge two object into one, ignore null properties.
 *
 * @author Vincent Moittié
 *
 */
public class NullAwareBeanUtilsBean extends BeanUtilsBean {

    /**
     * Ignore fields.
     */
    private final String[] ignoreFields;

    /**
     * Default constructor.
     */
    public NullAwareBeanUtilsBean() {
        this(new String[0]);
    }

    /**
     * Add ignore fields for merge.
     *
     * @param ignoreFields
     *            Names of field to ignore merge.
     */
    public NullAwareBeanUtilsBean(final String... ignoreFields) {
        this.ignoreFields = ignoreFields;
    }

    @Override
    public final void copyProperty(final Object dest, final String name, final Object value)
            throws IllegalAccessException, InvocationTargetException {
        if (ArrayUtils.contains(this.ignoreFields, name) || value == null) {
            return;
        }

        super.copyProperty(dest, name, value);
    }
}
