package com.octo.model.common;

/**
 * All constants value.
 *
 * @author Vincent Moittié
 */
public final class Constants {

    /**
     * Token length.
     */
    public static final int TOKEN_LENGTH = 50;
    /**
     * Minimum password length.
     */
    public static final int MINIMUM_PASSWORD_LENGTH = 8;
    /**
     * Maximum password length.
     */
    public static final int MAXIMUM_PASSWORD_LENGTH = 50;
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
     * Maximum resource size.
     */
    public static final int MAXIMUM_RESOURCE_SIZE = 200;
    /**
     * Default resource size.
     */
    public static final int DEFAULT_RESOURCE_SIZE = 10;

    /**
     * Private constructor.
     */
    private Constants() {
        throw new UnsupportedOperationException();
    }
}
