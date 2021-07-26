package com.octo.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.octo.dao.IDAO;
import com.octo.models.error.ErrorType;
import com.octo.models.error.GlobalException;
import com.octo.utils.bean.BeanMapper;
import com.octo.utils.predicate.filter.QueryFilter;
import com.octo.model.dto.common.SearchByNameDTO;
import com.octo.model.dto.project.NewProjectDTO;
import com.octo.model.dto.project.ProjectDTO;
import com.octo.model.entity.Project;
import com.octo.utils.Constants;

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
     * Group service.
     */
    @Autowired
    private IGroupService groupService;

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

        entity = this.projectDAO.save(entity);

        if (dto.getIsMaster()) {
            groupService.create(entity);
        } else if (StringUtils.isNotBlank(dto.getMasterName())) {
            Optional<Project> masterProject = this.projectDAO.load(new SearchByNameDTO(dto.getMasterName()));
            if (!masterProject.isPresent()) {
                throw new GlobalException(ErrorType.ENTITY_NOT_FOUND, Constants.FIELD_PROJECT, dto.getMasterName());
            }
            groupService.addProjectToGroup(masterProject.get(), entity);
        }

        return new BeanMapper<>(ProjectDTO.class).apply(entity);
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
