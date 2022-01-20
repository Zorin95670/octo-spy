package com.octo.model.common;

/**
 * Constant class to store all roles.
 *
 * @author Vincent Moittié
 */
public final class UserRoleType {

    /**
     * Used for route that permit token authentication. Need to be explicit.
     */
    public static final String TOKEN = "TOKEN";
    /**
     * Used for route with only valid connection with no roles. By default, token
     * are not allowed.
     */
    public static final String ALL = "ALL";
    /**
     * Authorizations. By default, token are not allowed.
     */
    public static final String ADMIN = "ADMIN";

    /**
     * Private constructor.
     */
    private UserRoleType() {
        throw new IllegalStateException("Utility class");
    }
}
