package com.octo.models.exception;

import javax.ws.rs.core.Response.Status;

/**
 * Default application exception.
 *
 * @author vmoittie
 *
 */
public abstract class ControllerException extends Exception {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -6050355569225117225L;
    /**
     * HTTP status of exception.
     */
    private final Status status;
    /**
     * Error of exception.
     */
    private final Error error;

    /**
     * Create exception with specific status.
     *
     * @param status
     *            HTTP status.
     */
    public ControllerException(final Status status) {
        super();
        this.status = status;
        this.error = new Error();
    }

    /**
     * Get HTTP status of exception.
     *
     * @return HTTP status.
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * Get error of exception.
     *
     * @return Error.
     */
    public Error getError() {
        return this.error;
    }
}
