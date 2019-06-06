package com.octo.model.error;

import java.io.Serializable;

/**
 * Model of error.
 *
 * @author vmoittie
 *
 */
public class Error implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 65949594742461639L;
    /**
     * Error's message.
     */
    private String message;
    /**
     * Field's name.
     */
    private String field;
    /**
     * Field's value.
     */
    private String value;

    /**
     * Cause of the error.
     */
    private String cause;

    /**
     * Default constructor.
     */
    public Error() {
        this(null, null, null, null);
    }

    /**
     * Constructor who init all member.
     *
     * @param message
     *            Mesage.
     * @param field
     *            Field.
     * @param value
     *            Value.
     * @param cause
     *            Cause.
     */
    public Error(final String message, final String field, final String value, final Throwable cause) {
        this.setMessage(message);
        this.setField(field);
        this.setValue(value);
        this.setCause(cause);
    }

    /**
     * Get error's message.
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
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * Get field's name.
     *
     * @return Field's name.
     */
    public String getField() {
        return this.field;
    }

    /**
     * Set field's name.
     *
     * @param field
     *            Field's name.
     */
    public void setField(final String field) {
        this.field = field;
    }

    /**
     * Get field's value, can be null.
     *
     * @return Field's value.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Set field's value, can be set to null.
     *
     * @param value
     *            Field's value.
     */
    public void setValue(final String value) {
        this.value = value;
    }

    /**
     * Get the cause of the error.
     *
     * @return Message.
     */
    public String getCause() {
        return this.cause;
    }

    /**
     * Set the cause of the error.
     *
     * @param cause
     *            Message.
     */
    public void setCause(final String cause) {
        this.cause = cause;
    }

    /**
     * Set the cause of the error.
     *
     * @param cause
     *            Message.
     */
    public void setCause(final Throwable cause) {
        String messageCause = null;
        if (cause != null) {
            messageCause = cause.getMessage();
        }

        this.setCause(messageCause);
    }
}
