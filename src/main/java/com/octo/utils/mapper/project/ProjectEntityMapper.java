package com.octo.utils.mapper.project;

import java.util.function.Function;

import com.octo.model.dto.project.NewProjectDTO;
import com.octo.model.entity.Project;

/**
 * Map project DTO to entity.
 *
 * @author vmoittie
 *
 */
public class ProjectEntityMapper implements Function<NewProjectDTO, Project> {

    @Override
    public final Project apply(final NewProjectDTO dto) {
        if (dto == null) {
            return null;
        }

        Project entity = new Project();
        entity.setName(dto.getName());

        return entity;
    }

}
