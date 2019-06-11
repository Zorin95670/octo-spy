package com.octo.utils.mapper.project;

import java.util.function.Function;

import com.octo.model.dto.project.ProjectDTO;
import com.octo.model.entity.Project;

/**
 * Map project entity to DTO.
 *
 * @author vmoittie
 *
 */
public class ProjectDTOMapper implements Function<Project, ProjectDTO> {

    @Override
    public final ProjectDTO apply(final Project entity) {
        if (entity == null) {
            return null;
        }

        ProjectDTO dto = new ProjectDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setInsertDate(entity.getInsertDate());
        dto.setUpdateDate(entity.getUpdateDate());

        return dto;
    }

}
