package com.octo.model.dto.project;

import com.octo.model.dto.common.AbstractDateDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Project DTO.
 *
 * @author Vincent Moittié
 *
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public @Data class ProjectDTO extends AbstractDateDTO {
    /**
     * Primary key.
     */
    private Long id;
    /**
     * Project's name.
     */
    private String name;
    /**
     * Deployment's project color.
     */
    private String color;
}
