package com.octo.model.dto.alert;

/**
 * Alert DTO.
 *
 * @author Vincent moittié
 *
 */
public class AlertDTO {

    /**
     * Severity.
     */
    private String severity;

    /**
     * Type.
     */
    private String type;

    /**
     * Message.
     */
    private String message;

    /**
     * Get severity.
     *
     * @return Severity.
     */
    public String getSeverity() {
        return severity;
    }

    /**
     * Set severity.
     *
     * @param severity
     *            Severity
     */
    public void setSeverity(final String severity) {
        this.severity = severity;
    }

    /**
     * Get type.
     *
     * @return Type
     */
    public String getType() {
        return type;
    }

    /**
     * Set type.
     *
     * @param type
     *            Type.
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * Get message.
     *
     * @return Message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set message.
     *
     * @param message
     *            Message.
     */
    public void setMessage(final String message) {
        this.message = message;
    }

}
