package com.octo.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.octo.utils.Constants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Project model.
 *
 * @author Vincent Moittié
 *
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "projects_view")
public @Data class ProjectView extends AbstractProject {
    /**
     * Is master's project.
     */
    @Column(name = "is_master", nullable = false, length = Constants.DEFAULT_SIZE_OF_STRING)
    private boolean isMaster;
    /**
     * Master project's name.
     */
    @Column(name = "master_project", nullable = false, length = Constants.DEFAULT_SIZE_OF_STRING)
    private String masterProject;
    /**
     * Master project's color.
     */
    @Column(name = "master_project_color", nullable = false, length = Constants.DEFAULT_SIZE_OF_STRING)
    private String masterProjectColor;
}
