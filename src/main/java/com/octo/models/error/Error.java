package com.octo.models.error;

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
}
