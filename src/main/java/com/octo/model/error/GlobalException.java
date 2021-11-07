package com.octo.model.error;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.ws.rs.core.Response.Status;

/**
 * Default application exception.
 *
 * @author Vincent Moittié
 *
 */
public class GlobalException extends RuntimeException {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -6050355569225117225L;
    /**
     * HTTP status of exception.
     */
    private final Status status;
    /**
     * Error of exception.
     */
    private final ErrorDTO error;
    /**
     * Log exception.
     */
    private final boolean logIt;

    /**
     * Parameters error exception.
     *
     * @param error
     *            Error's type.
     * @param field
     *            Field's name.
     */
    public GlobalException(final ErrorType error, final String field) {
        this(null, error, field, true);
    }

    /**
     * Parameters error exception.
     *
     * @param error
     *            Error's type.
     * @param field
     *            Field's name.
     * @param needToBeLogged
     *            Indicates if exception need to logged.
     */
    public GlobalException(final ErrorType error, final String field, final boolean needToBeLogged) {
        this(null, error, field, needToBeLogged);
    }

    /**
     * Parameters error exception.
     *
     * @param exception
     *            The cause of this error.
     * @param error
     *            Error's type.
     * @param field
     *            Field's name.
     */
    public GlobalException(final Throwable exception, final ErrorType error, final String field) {
        this(exception, error, field, null, true);
    }

    /**
     * Parameters error exception.
     *
     * @param exception
     *            The cause of this error.
     * @param error
     *            Error's type.
     * @param field
     *            Field's name.
     * @param needToBeLogged
     *            Indicates if exception need to logged.
     */
    public GlobalException(final Throwable exception, final ErrorType error, final String field,
            final boolean needToBeLogged) {
        this(exception, error, field, null, needToBeLogged);
    }

    /**
     * Parameters error exception.
     *
     * @param error
     *            Error's type.
     * @param field
     *            Field's name.
     * @param value
     *            Field's value.
     */
    public GlobalException(final ErrorType error, final String field, final String value) {
        this(null, error, field, value, true);
    }

    /**
     * Parameters error exception.
     *
     * @param error
     *            Error's type.
     * @param field
     *            Field's name.
     * @param value
     *            Field's value.
     * @param needToBeLogged
     *            Indicates if exception need to logged.
     */
    public GlobalException(final ErrorType error, final String field, final String value,
            final boolean needToBeLogged) {
        this(null, error, field, value, needToBeLogged);
    }

    /**
     * Parameters error exception.
     *
     * @param exception
     *            The cause of this error.
     * @param error
     *            Error's type.
     * @param field
     *            Field's name.
     * @param value
     *            Field's value.
     */
    public GlobalException(final Throwable exception, final ErrorType error, final String field, final String value) {
        this(exception, error, field, value, true);
    }

    /**
     * Parameters error exception.
     *
     * @param exception
     *            The cause of this error.
     * @param error
     *            Error's type.
     * @param field
     *            Field's name.
     * @param value
     *            Field's value.
     * @param needToBeLogged
     *            Indicates if exception need to logged.
     */
    public GlobalException(final Throwable exception, final ErrorType error, final String field, final String value,
            final boolean needToBeLogged) {
        super(error.getMessage(), exception);
        this.status = error.getStatus();
        this.error = new ErrorDTO(error.getMessage(), field, value, exception);
        this.logIt = needToBeLogged;
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
    public ErrorDTO getError() {
        return this.error;
    }

    /**
     * Indicate if exception need to be logged.
     *
     * @return Visibility of exception in log.
     */
    public boolean needToBeLogged() {
        return this.logIt;
    }
}
