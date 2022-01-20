package com.octo.model.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Record to create project.
 *
 * @param name       Project name.
 * @param color      Project color.
 * @param isMaster   Indicate if project is a master project.
 * @param masterName Master project name.
 * @author Vincent Moittié
 */
@JsonIgnoreProperties
public record ProjectRecord(@NotBlank String name, @Pattern(regexp = "[0-9]{1,3}(,[0-9]{1,3}){2}") String color,
                            boolean isMaster, String masterName) {

}
