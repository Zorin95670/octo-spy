package com.octo.utils.retry;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Perform retry if the action failed. Retries are limited by the configuration.
 *
 * @param <T>
 *            Result of the action to return.
 */
public class RetryExecutor<T> {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(RetryExecutor.class);

    /**
     * Current retry.
     */
    private int retryCounter = 1;
    /**
     * Max retry.
     */
    private int maxTry;
    /**
     * Number of milliseconds before each retry.
     */
    private int delayBeforeRetry;
    /**
     * Exception to throw when retry limit is reached.
     */
    private RuntimeException exception;
    /**
     * Retry identifier.
     */
    private final UUID identifier = UUID.randomUUID();

    /**
     * Construct RetryExecutor with <b>configuration</b> and <b>exception</b>. <b>Configuration</b> provides delay
     * between each retries in milliseconds. When the retry limit is reached, provided <b>exception</b> is thrown.
     *
     * @param maxTry
     *            Maximum number of retries.
     * @param delayBeforeRetry
     *            Delay between retries
     * @param exception
     *            To throw when retry limit is reached.
     */
    public RetryExecutor(final int maxTry, final int delayBeforeRetry, final RuntimeException exception) {
        LOGGER.info("Create retry executor with {}", identifier);
        this.exception = exception;
        this.maxTry = maxTry;
        this.delayBeforeRetry = delayBeforeRetry;
    }

    /**
     * Perform the action and if the action does not throw a RetryException, return the result provided by the action.
     * Else, if the action throws a RetryException, it performs a retry.
     *
     * @param action
     *            Action to perform.
     * @return Result of the action.
     * @throws InterruptedException
     *             If interrupted while sleeping before next retry.
     */
    public final T execute(final Supplier<T> action) throws InterruptedException {
        try {
            LOGGER.info("Retry {}: perform retry {}/{}", identifier, retryCounter, this.maxTry);
            return action.get();
        } catch (RetryException e) {
            LOGGER.warn("Retry {}: failed retry {}/{}", identifier, retryCounter, this.maxTry, e);
        }

        ++retryCounter;

        if (retryCounter > this.maxTry) {
            LOGGER.error("Retry {}: Failed to retry", identifier);
            throw exception;
        }

        TimeUnit.MILLISECONDS.sleep(delayBeforeRetry);

        return execute(action);
    }
}
