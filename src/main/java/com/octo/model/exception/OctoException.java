package com.octo.model.exception;

import javax.ws.rs.core.Response.Status;

import com.octo.model.error.ErrorType;
import com.octo.model.error.Error;

/**
 * Default application exception.
 *
 * @author vmoittie
 *
 */
public class OctoException extends RuntimeException {

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
     * @param exception
     *            The cause.
     * @param error
     *            Error type.
     * @param field
     *            Field's name.
     * @param value
     *            Value.
     */
    public OctoException(final Exception exception, final ErrorType error, final String field, final String value) {
        super(error.getMessage(), exception);
        this.status = error.getStatus();
        this.error = new Error(error.getMessage(), field, value, null);
    }

    /**
     * Create exception with specific status.
     *
     * @param error
     *            Error type.
     * @param field
     *            Field's name.
     * @param value
     *            Value.
     */
    public OctoException(final ErrorType error, final String field, final String value) {
        this(null, error, field, value);
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
