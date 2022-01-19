package com.octo.model.environment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * Record to create environment.
 *
 * @param name     Environment name.
 * @param position Environment order.
 * @author Vincent Moittié
 */
@JsonIgnoreProperties
public record EnvironmentRecord(@NotNull @NotBlank String name, @Min(0) int position) {

}
