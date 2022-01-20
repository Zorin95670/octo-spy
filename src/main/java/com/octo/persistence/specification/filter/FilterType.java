package com.octo.persistence.specification.filter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate if field is filtering.
 *
 * @author Vincent Moittié
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FilterType {

    /**
     * Type of filter.
     *
     * @return Type of filter.
     */
    Type type();

    /**
     * Type of filter.
     */
    enum Type {
        /**
         * Text.
         */
        TEXT,
        /**
         * Date.
         */
        DATE,
        /**
         * Number.
         */
        NUMBER,
        /**
         * Boolean.
         */
        BOOLEAN,
        /**
         * Token.
         */
        TOKEN,
    }
}
