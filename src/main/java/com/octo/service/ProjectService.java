package com.octo.service;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cji.dao.IDAO;
import com.cji.models.error.ErrorType;
import com.cji.models.error.GlobalException;
import com.cji.utils.bean.BeanMapper;
import com.cji.utils.predicate.filter.QueryFilter;
import com.octo.model.dto.project.NewProjectDTO;
import com.octo.model.dto.project.ProjectDTO;
import com.octo.model.entity.Project;

/**
 * Project service.
 */
@Service
@Transactional
public class ProjectService {

    /**
     * Project DAO.
     */
    @Autowired
    private IDAO<Project, QueryFilter> projectDAO;

    /**
     * Load project by id.
     *
     * @param id
     *            Primary key.
     * @return Project.
     * @throws OctoException
     *             On all database error.
     */
    public ProjectDTO load(final Long id) {
        return new BeanMapper<>(ProjectDTO.class).apply(projectDAO.loadEntityById(id));
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
            throw new GlobalException(ErrorType.EMPTY_VALUE, "name", null);
        }

        Project entity = new BeanMapper<>(Project.class).apply(dto);

        return new BeanMapper<>(ProjectDTO.class).apply(this.projectDAO.save(entity));
    }

    /**
     * Delete an project.
     *
     * @param id
     *            Id of project to update.
     */
    public void delete(final Long id) {
        final Project entity = this.projectDAO.loadEntityById(id);
        this.projectDAO.delete(entity);
    }
}
