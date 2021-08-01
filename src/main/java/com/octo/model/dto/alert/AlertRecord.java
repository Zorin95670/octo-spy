package com.octo.model.dto.alert;

/**
 * Alert record.
 *
 * @param severity
 *            Severity.
 * @param type
 *            Type.
 * @param message
 *            Message.
 * @author vmoittie
 *
 */
public record AlertRecord(String severity, String type, String message) {

}
