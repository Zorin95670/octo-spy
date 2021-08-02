package com.octo.model.dto.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Record to create project.
 *
 * @param name
 *            Projet name.
 * @param isMaster
 *            Indicate if project is a master project.
 * @param masterName
 *            Master project name.
 * @author Vincent Moittié
 *
 */
@JsonIgnoreProperties
public record NewProjectRecord(String name, boolean isMaster, String masterName) {

}
