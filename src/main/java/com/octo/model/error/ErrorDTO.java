package com.octo.model.error;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Model of error.
 *
 * @author Vincent Moittié
 */
@Data
public class ErrorDTO implements Serializable {

    /**
     * Serial version UID.
     */
    @Serial
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
    }

    /**
     * Constructor that init all member.
     *
     * @param message Message.
     * @param field   Field.
     * @param value   Value.
     * @param cause   Cause.
     */
    public ErrorDTO(final String message, final String field, final String value, final Throwable cause) {
        this.setMessage(message);
        this.setField(field);
        this.setValue(value);
        this.setThrowable(cause);
    }

    /**
     * Set the cause of the error.
     *
     * @param throwable Message.
     */
    public void setThrowable(final Throwable throwable) {
        String messageCause = null;
        if (throwable != null) {
            messageCause = throwable.getMessage();
        }

        this.setCause(messageCause);
    }
}
