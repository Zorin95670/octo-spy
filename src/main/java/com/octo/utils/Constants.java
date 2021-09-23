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
     * Token length.
     */
    public static final int TOKEN_LENGHT = 50;
    /**
     * Special accepted characters for token generation.
     */
    public static final String SPECIAL_CHARACTERS = "_-,;?!.%=^";
    /**
     * Minimum password length.
     */
    public static final int MINIMUM_PASSWORD_LENGTH = 8;

    /**
     * Maximum password length.
     */
    public static final int MAXMUM_PASSWORD_LENGTH = 50;
    /**
     * Minimum database version.
     */
    public static final int DATABASE_VERSION_MINIMUM = 12;
    /**
     * Maximum database version.
     */
    public static final int DATABASE_VERSION_MAXIMUM = 13;
    /**
     * Default administrator login.
     */
    public static final String DEFAULT_ADMIN_LOGIN = "admin";
    /**
     * Authorization property value.
     */
    public static final String AUTHORIZATION_PROPERTY = "Authorization";
    /**
     * Authorization basic scheme value.
     */
    public static final String AUTHENTICATION_BASIC_SCHEME = "Basic";
    /**
     * Authorization token scheme value.
     */
    public static final String AUTHENTICATION_TOKEN_SCHEME = "Token";
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
