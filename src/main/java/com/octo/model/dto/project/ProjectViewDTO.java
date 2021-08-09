package com.octo.model.dto.project;

/**
 * Project DTO.
 *
 * @author Vincent Moittié
 *
 */
public class ProjectViewDTO extends ProjectDTO {

    /**
     * Is master project.
     */
    private boolean isMaster;

    /**
     * Master project's name.
     */
    private String masterProject;

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
}
