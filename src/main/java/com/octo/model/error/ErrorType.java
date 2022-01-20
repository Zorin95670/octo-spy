package com.octo.model.error;

import javax.ws.rs.core.Response.Status;

/**
 * List of controller exception error.
 *
 * @author Vincent Moittié
 */
public enum ErrorType {
    /**
     * Error to call when authorization request is bad.
     */
    AUTHORIZATION_ERROR("Authentication error.", Status.UNAUTHORIZED),
    /**
     * Error to call when user has bad authentication token.
     */
    AUTHORIZATION_FAILED("Authentication has failed.", Status.UNAUTHORIZED),
    /**
     * Error to call when a field value is empty.
     */
    EMPTY_VALUE("Field value is empty.", Status.BAD_REQUEST),
    /**
     * Error to call when field value contains a wrong filter value.
     */
    WRONG_FILTER_VALUE("Field value is wrong.", Status.BAD_REQUEST),
    /**
     * Error to call when operator of wrong filter is wrong.
     */
    WRONG_FILTER_OPERATOR("Field contains a wrong operator.", Status.BAD_REQUEST),
    /**
     * Error to call when entity is not found in database.
     */
    ENTITY_NOT_FOUND("Entity not found.", Status.NOT_FOUND),
    /**
     * Error to call when there are problems with field.
     */
    WRONG_FIELD("Somethings wrong with this field.", Status.BAD_REQUEST),
    /**
     * Error to call when an internal error occurred.
     */
    INTERNAL_ERROR("Internal error occurred, please contact your administrator.", Status.INTERNAL_SERVER_ERROR),
    /**
     * Error to call when field is not an attribute of model.
     */
    UNKNOWN_FIELD("Field is not an attribute of model.", Status.BAD_REQUEST),
    /**
     * Error to call when field value is wrong.
     */
    WRONG_VALUE("Wrong field value.", Status.BAD_REQUEST);

    /**
     * Exception message.
     */
    private final String message;

    /**
     * Exception status.
     */
    private final Status status;

    /**
     * Error type with message.
     *
     * @param message Error's message.
     * @param status  Error's status.
     */
    ErrorType(final String message, final Status status) {
        this.message = message;
        this.status = status;
    }

    /**
     * Get message error.
     *
     * @return Message.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Get status error.
     *
     * @return Status.
     */
    public Status getStatus() {
        return this.status;
    }
}
