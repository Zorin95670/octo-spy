package com.octo.model.dto.project;

import com.cji.models.common.DefaultDTO;

/**
 * Create project DTO.
 */
public class NewProjectDTO extends DefaultDTO {

    /**
     * Project's name.
     */
    private String name;

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
}
