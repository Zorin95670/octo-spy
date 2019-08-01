package com.octo.model.dto.environment;

import com.octo.model.dto.common.DefaultDTO;

import io.swagger.v3.oas.annotations.Parameter;

/**
 * Default Environment DTO.
 *
 * @author vmoittie
 *
 */
public class EnvironmentDTO extends DefaultDTO {

    /**
     * Primary key.
     */
    @Parameter(description = "Environment's id.")
    private Long id;
    /**
     * Environment's name.
     */
    @Parameter(description = "Environment's name.")
    private String name;

    /**
     * Default constructor.
     */
    public EnvironmentDTO() {
        this(null, null);
    }

    /**
     * Constructor that initialize field.
     *
     * @param id
     *            Id's value.
     * @param name
     *            Name's value.
     */
    public EnvironmentDTO(final Long id, final String name) {
        setId(id);
        setName(name);
    }

    /**
     * Get environment's id.
     *
     * @return Environment's id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set environment's id.
     *
     * @param id
     *            Environment's id.
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Get environment's name.
     *
     * @return Environment's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set environment's name.
     *
     * @param name
     *            Environment's name.
     */
    public void setName(final String name) {
        this.name = name;
    }
}
