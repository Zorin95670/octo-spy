package com.octo.models.error;

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
    INTERNAL("An unknow error has appear.");

    /**
     * Exception message.
     */
    private String message;

    /**
     * Error type with message.
     *
     * @param message
     *            Error's message.
     */
    ErrorType(final String message) {
        this.setMessage(message);
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
     * Set error's message.
     *
     * @param message
     *            Message.
     */
    private void setMessage(final String message) {
        this.message = message;
    }
}
