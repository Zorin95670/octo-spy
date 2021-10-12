package com.octo.model.dto.environment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * Record to create environment.
 *
 * @param name
 *            Environment name.
 * @param position
 *            Environment order.
 * @author Vincent Moittié
 *
 */
@JsonIgnoreProperties
public record NewEnvironmentRecord(String name, int position) {

}
