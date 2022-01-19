package com.octo.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.octo.model.common.Constants;
import com.octo.persistence.specification.filter.FilterType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Project model.
 *
 * @author Vincent Moittié
 */
@Entity
@Table(name = "projects_view")
@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectView extends AbstractProject {
    /**
     * Is master's project.
     */
    @Column(name = "is_master", nullable = false, length = Constants.DEFAULT_SIZE_OF_STRING)
    @FilterType(type = FilterType.Type.BOOLEAN)
    @JsonProperty("isMaster")
    private boolean isMaster;
    /**
     * Master project's name.
     */
    @Column(name = "master_project", nullable = false, length = Constants.DEFAULT_SIZE_OF_STRING)
    @FilterType(type = FilterType.Type.TEXT)
    private String masterProject;
    /**
     * Master project's color.
     */
    @Column(name = "master_project_color", nullable = false, length = Constants.DEFAULT_SIZE_OF_STRING)
    @FilterType(type = FilterType.Type.TEXT)
    private String masterProjectColor;
}
