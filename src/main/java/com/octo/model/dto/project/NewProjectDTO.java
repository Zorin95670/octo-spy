package com.octo.model.dto.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.octo.model.common.DefaultDTO;

/**
 * Create project DTO.
 */
@JsonIgnoreProperties
public class NewProjectDTO extends DefaultDTO {

    /**
     * Project's name.
     */
    private String name;
    /**
     * Is master of his groups.
     */
    private boolean isMaster;

    /**
     * Name of project's master.
     */
    private String masterName;

    /**
     * Get project's name.
     *
     * @return Project's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set project's name.
     *
     * @param name
     *            Project's name.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Indicate if this project is master of his groups.
     *
     * @return Master state.
     */
    public boolean getIsMaster() {
        return isMaster;
    }

    /**
     * Set master of this groups.
     *
     * @param isMaster
     *            Master state.
     */
    public void setIsMaster(boolean isMaster) {
        this.isMaster = isMaster;
    }

    /**
     * If groups is not a master, return name of project's master.
     *
     * @return Master's project name.
     */
    public String getMasterName() {
        return masterName;
    }

    /**
     * Set master's project name.
     *
     * @param masterName
     *            Master's project name.
     */
    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }
}
