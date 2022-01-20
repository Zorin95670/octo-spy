package com.octo.model.alert;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Alert.
 *
 * @author vmoittie
 */
@AllArgsConstructor
@Data
public class Alert {
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
}
