package com.octo.dao;

/**
 * Parameter for stored procedure.
 *
 * @param name
 *            Parameter name
 * @param value
 *            Value of parameter.
 *
 * @author Vincent Moittié
 */
public record ProcedureParameter(String name, Object value) {
}
