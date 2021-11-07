package com.octo.model.error;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Model of error.
 *
 * @author Vincent Moittié
 *
 */
public @Data class ErrorDTO implements Serializable {

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
    public ErrorDTO() {
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
    public ErrorDTO(final String message, final String field, final String value, final Throwable cause) {
        this.setMessage(message);
        this.setField(field);
        this.setValue(value);
        this.setCause(cause);
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
