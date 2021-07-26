package com.octo.utils.retry;

/**
 * Exception to throw in code executed by RetryExecutor to perform a retry.
 *
 * @author Vincent Moittié
 *
 */
public class RetryException extends RuntimeException {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -5149239305940776064L;

    /**
     * Default constructor.
     *
     * @param message
     *            The detail message.
     */
    public RetryException(final String message) {
        this(message, null);
    }

    /**
     * Construct a new retry exception with the specified cause.
     *
     * @param throwable
     *            Cause.
     */
    public RetryException(final Throwable throwable) {
        this(null, throwable);
    }

    /**
     * Construct a new retry exception with the specified cause.
     *
     * @param message
     *            The detail message.
     * @param throwable
     *            Cause.
     */
    public RetryException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
