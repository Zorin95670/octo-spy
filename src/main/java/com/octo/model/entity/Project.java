package com.octo.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Project model.
 *
 * @author Vincent Moittié
 *
 */
@Entity
@Table(name = "projects")
public class Project extends AbstractProject implements IPrePersistance {

}
