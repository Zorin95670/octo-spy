package com.octo.model.dto.environment;

import com.octo.model.common.DefaultDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Default Environment DTO.
 *
 * @author Vincent Moittié
 *
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public @Data class EnvironmentDTO extends DefaultDTO {

    /**
     * Primary key.
     */
    private Long id;
    /**
     * Environment's name.
     */
    private String name;
    /**
     * Environment's position.
     */
    private int position;

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
}
