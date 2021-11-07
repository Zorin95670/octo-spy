package com.octo.model.dto.project;

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
public @Data class ProjectViewDTO extends ProjectDTO {

    /**
     * Is master project.
     */
    private boolean isMaster;

    /**
     * Master project's name.
     */
    private String masterProject;
}
