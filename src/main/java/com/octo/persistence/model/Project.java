package com.octo.persistence.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Project model.
 *
 * @author Vincent Moittié
 */
@Entity
@Table(name = "projects")
@EqualsAndHashCode(callSuper = true)
@Data
public class Project extends AbstractProject {

}
