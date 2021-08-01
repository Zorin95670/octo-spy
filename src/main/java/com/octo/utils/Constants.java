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
     * Default administrator login.
     */
    public static final String DEFAULT_ADMIN_LOGIN = "admin";
    /**
     * Authorization property value.
     */
    public static final String AUTHORIZATION_PROPERTY = "Authorization";
    /**
     * Authorization scheme value.
     */
    public static final String AUTHENTICATION_SCHEME = "Basic";
    /**
     * Default length of string in database.
     */
    public static final int DEFAULT_SIZE_OF_STRING = 100;

    /**
     * Long length of string in database.
     */
    public static final int LONG_SIZE_OF_STRING = 255;
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
    /**
     * Field key.
     */
    public static final String FIELD_KEY = "field";
}
