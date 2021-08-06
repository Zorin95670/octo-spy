package com.octo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.octo.dao.IDAO;
import com.octo.model.dto.common.SearchByNameDTO;
import com.octo.model.dto.project.NewProjectRecord;
import com.octo.model.dto.project.ProjectDTO;
import com.octo.model.dto.project.ProjectViewDTO;
import com.octo.model.dto.project.SearchProjectViewDTO;
import com.octo.model.entity.Project;
import com.octo.model.entity.ProjectView;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.utils.Constants;
import com.octo.utils.bean.BeanMapper;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * Project service.
 *
 * @author Vincent Moittié
 *
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
     * Project DAO.
     */
    @Autowired
    private IDAO<ProjectView, QueryFilter> projectViewDAO;

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
     */
    public ProjectDTO save(final NewProjectRecord dto) {
        if (dto == null || StringUtils.isBlank(dto.name())) {
            throw new GlobalException(ErrorType.EMPTY_VALUE, "name", null);
        }

        Project entity = new BeanMapper<>(Project.class).apply(dto);
        entity = this.projectDAO.save(entity);

        if (dto.isMaster()) {
            groupService.create(entity);
        } else if (StringUtils.isNotBlank(dto.masterName())) {
            Optional<Project> masterProject = this.projectDAO.load(new SearchByNameDTO(dto.masterName()));
            if (!masterProject.isPresent()) {
                throw new GlobalException(ErrorType.ENTITY_NOT_FOUND, Constants.FIELD_PROJECT, dto.masterName());
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

    /**
     * Get all projects.
     *
     * @param dto
     *            Project filter.
     * @return Projects
     */
    public List<ProjectViewDTO> findAll(final SearchProjectViewDTO dto) {
        return this.projectViewDAO.find(dto, true).stream().map(new BeanMapper<>(ProjectViewDTO.class)).toList();
    }
}
