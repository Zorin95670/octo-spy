package com.octo.model.dto.project;

/**
 * Create project DTO.
 *
 * @author vmoittie
 *
 */
public class NewProjectDTO {

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
