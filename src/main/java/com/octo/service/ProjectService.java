package com.octo.service;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.octo.dao.IDAO;
import com.octo.model.dto.common.DefaultDTO;
import com.octo.model.dto.project.NewProjectDTO;
import com.octo.model.dto.project.ProjectDTO;
import com.octo.model.entity.Project;
import com.octo.model.error.ErrorType;
import com.octo.model.exception.OctoException;
import com.octo.utils.mapper.project.ProjectDTOMapper;
import com.octo.utils.mapper.project.ProjectEntityMapper;

/**
 * Project service.
 *
 * @author vmoittie
 *
 */
@Service
@Transactional
public class ProjectService {

    /**
     * Project DAO.
     */
    @Autowired
    private IDAO<Project, DefaultDTO> projectDAO;

    /**
     * Load project by id.
     *
     * @param id
     *            Primary key.
     * @return Project.
     * @throws OctoException
     *             On all database error.
     */
    public ProjectDTO loadById(final Long id) {
        if (id == null) {
            throw new OctoException(ErrorType.EMPTY_VALUE, "id", null);
        }

        Project entity = this.projectDAO.loadById(id);

        if (entity == null) {
            throw new OctoException(ErrorType.ENTITY_NOT_FOUND, "id", id.toString());
        }

        return new ProjectDTOMapper().apply(entity);
    }

    /**
     * Save project in database.
     *
     * @param dto
     *            DTO to save
     * @return Project.
     * @throws OctoException
     *             On all database error.
     */
    public ProjectDTO save(final NewProjectDTO dto) {
        if (dto == null || StringUtils.isBlank(dto.getName())) {
            throw new OctoException(ErrorType.EMPTY_VALUE, "name", null);
        }

        Project entity = new ProjectEntityMapper().apply(dto);

        entity = this.projectDAO.save(entity);

        return new ProjectDTOMapper().apply(entity);
    }

    /**
     * Delete an project.
     *
     * @param id
     *            Id of project to update.
     */
    public void delete(final Long id) {
        final Project entity = this.projectDAO.loadById(id);
        this.projectDAO.delete(entity);
    }
}
