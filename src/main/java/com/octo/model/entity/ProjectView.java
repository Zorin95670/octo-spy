package com.octo.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.octo.utils.Constants;

/**
 * Project model.
 *
 * @author Vincent Moittié
 *
 */
@Entity
@Table(name = "projects_view")
public class ProjectView extends AbstractProject {
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

    /**
     * Indicate if project is a master project.
     *
     * @return Boolean.
     */
    public boolean getIsMaster() {
        return isMaster;
    }

    /**
     * Set if project is master.
     *
     * @param isMaster
     *            Boolean.
     */
    public void setIsMaster(final boolean isMaster) {
        this.isMaster = isMaster;
    }
    /**
     * Get master project's name.
     *
     * @return Master project's name.
     */
    public String getMasterProject() {
        return masterProject;
    }

    /**
     * Set master project's name.
     *
     * @param masterProject
     *            Master project's name.
     */
    public void setMasterProject(final String masterProject) {
        this.masterProject = masterProject;
    }

    /**
     * Get master project's color.
     *
     * @return Master project's color.
     */
    public String getMasterProjectColor() {
        return masterProjectColor;
    }

    /**
     * Set master project's color.
     *
     * @param masterProjectColor
     *            Master project's color.
     */
    public void setMasterProjectColor(final String masterProjectColor) {
        this.masterProjectColor = masterProjectColor;
    }

}
