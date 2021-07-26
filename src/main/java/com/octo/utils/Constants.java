package com.octo.utils;

/**
 * All constants value.
 *
 * @author Vincent Moittié
 *
 */
public final class Constants {

    /**
     * Private constructor.
     */
    private Constants() {
        throw new UnsupportedOperationException();
    }

    /**
     * Default length of string in database.
     */
    public static final int DEFAULT_SIZE_OF_STRING = 100;
    /**
     * Environment id start value.
     */
    public static final int START_VALUE = 10;
    /**
     * Project field's name.
     */
    public static final String FIELD_PROJECT = "project";
    /**
     * Environment field's name.
     */
    public static final String FIELD_ENVIRONMENT = "environment";
}
