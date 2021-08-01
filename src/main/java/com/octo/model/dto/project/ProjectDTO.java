package com.octo.model.dto.project;

/**
 * Project DTO.
 *
 * @author Vincent Moittié
 *
 */
public class ProjectDTO extends AbstractDateDTO {
    /**
     * Primary key.
     */
    private Long id;
    /**
     * Project's name.
     */
    private String name;

    /**
     * Get primary key value.
     *
     * @return Primary key value.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set primary key value.
     *
     * @param id
     *            Primary key value.
     */
    public void setId(final Long id) {
        this.id = id;
    }

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
