package com.octo.model.authentication;

/**
 * Constant class to store all roles.
 *
 * @author Vincent Moittié
 *
 */
public final class UserRoleType {

    /**
     * Private constructor.
     */
    private UserRoleType() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Used for route that permit token authentication. Need to be explicit.
     */
    public static final String TOKEN = "TOKEN";
    /**
     * Used for route with only valid connection with no roles. By default token
     * are not allowed.
     */
    public static final String ALL = "ALL";
    /**
     * Authorizations. By default token are not allowed.
     * <p>
     * <b>Deployment:</b>
     * </p>
     * <ul>
     * <li>create</li>
     * <li>delete</li>
     * <li>delete progress</li>
     * </ul>
     * <p>
     * <b>Project:</b>
     * </p>
     * <ul>
     * <li>create</li>
     * <li>delete</li>
     * </ul>
     * <p>
     * <b>User:</b>
     * </p>
     * <ul>
     * <li>Enabled/Disabled default administrator</li>
     * <li>Update default administrator password</li>
     * </ul>
     */
    public static final String ADMIN = "ADMIN";
    /**
     * Only for authorized project, authorizations.By default token are not
     * allowed.
     * <p>
     * <b>Deployment:</b>
     * </p>
     * <ul>
     * <li>create</li>
     * <li>delete</li>
     * <li>delete progress</li>
     * </ul>
     * <p>
     * <b>Project:</b>
     * </p>
     * <ul>
     * <li>create</li>
     * <li>delete</li>
     * </ul>
     */
    public static final String PROJECT_MANAGER = "PROJECT_MANAGER";
}
