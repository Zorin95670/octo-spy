package com.octo.model.dto.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Record to create project.
 *
 * @param name
 *            Project name.
 * @param color
 *            Project color.
 * @param isMaster
 *            Indicate if project is a master project.
 * @param masterName
 *            Master project name.
 * @author Vincent Moittié
 *
 */
@JsonIgnoreProperties
public record NewProjectRecord(String name, String color, boolean isMaster, String masterName) {

}
