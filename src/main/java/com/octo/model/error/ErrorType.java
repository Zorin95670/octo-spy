package com.octo.model.error;

import javax.ws.rs.core.Response.Status;

/**
 * List of controller exception error.
 *
 * @author vmoittie
 *
 */
public enum ErrorType {
    /**
     * Error to call when a uncatch error is throw.
     */
    INTERNAL_ERROR("Internal error occurs, please contact your administrator.", Status.INTERNAL_SERVER_ERROR);

    /**
     * Exception message.
     */
    private String message;

    /**
     * Exception status.
     */
    private Status status;

    /**
     * Error type with message.
     *
     * @param message
     *            Error's message.
     * @param status
     *            Error's status.
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
