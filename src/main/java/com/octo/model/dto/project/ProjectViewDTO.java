package com.octo.model.dto.project;

/**
 * Project DTO.
 *
 * @author Vincent Moittié
 *
 */
public class ProjectViewDTO extends ProjectDTO {

    /**
     * Master project's name.
     */
    private String masterProject;

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
