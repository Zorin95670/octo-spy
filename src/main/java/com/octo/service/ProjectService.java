package com.octo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.octo.dao.IDAO;
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
import com.octo.utils.bean.NullAwareBeanUtilsBean;
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

        Project entity = null;

        if (dto.isMaster()) {
            entity = this.saveMasterProject(dto);
        } else {
            entity = this.saveSubProject(dto);
        }

        return new BeanMapper<>(ProjectDTO.class).apply(entity);
    }

    /**
     * Save master project.
     *
     * @param dto
     *            Project to create.
     * @return Created project.
     */
    public Project saveMasterProject(final NewProjectRecord dto) {
        SearchProjectViewDTO projectFilter = new SearchProjectViewDTO();
        projectFilter.setIsMaster("true");
        projectFilter.setName(dto.name());
        Optional<ProjectView> masterProject = this.projectViewDAO.load(projectFilter);
        if (masterProject.isPresent()) {
            throw new GlobalException(ErrorType.WRONG_VALUE, "name", "duplicate");
        }

        Project entity = this.projectDAO.save(new BeanMapper<>(Project.class).apply(dto));
        groupService.create(entity);

        return entity;
    }

    /**
     * Save sub-project.
     *
     * @param dto
     *            Sub-project to create.
     * @return Created sub-project.
     */
    public Project saveSubProject(final NewProjectRecord dto) {
        if (StringUtils.isBlank(dto.masterName())) {
            throw new GlobalException(ErrorType.EMPTY_VALUE, "masterName");
        }
        SearchProjectViewDTO projectFilter = new SearchProjectViewDTO();
        projectFilter.setIsMaster("false");
        projectFilter.setName(dto.name());
        projectFilter.setMasterProject(dto.masterName());
        Optional<ProjectView> masterProject = this.projectViewDAO.load(projectFilter);
        if (masterProject.isPresent()) {
            throw new GlobalException(ErrorType.WRONG_VALUE, "name", "duplicate");
        }

        projectFilter = new SearchProjectViewDTO();
        projectFilter.setIsMaster("true");
        projectFilter.setName(dto.masterName());
        masterProject = this.projectViewDAO.load(projectFilter);
        if (!masterProject.isPresent()) {
            throw new GlobalException(ErrorType.ENTITY_NOT_FOUND, Constants.FIELD_PROJECT, dto.masterName());
        }
        Project entity = this.projectDAO.save(new BeanMapper<>(Project.class).apply(dto));
        groupService.addProjectToGroup(masterProject.get().getId(), entity);

        return entity;
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

    /**
     * Update project information.
     *
     * @param id
     *            Project's id.
     * @param projectRecord
     *            Project's information.
     */
    public void update(final Long id, final NewProjectRecord projectRecord) {
        Project project = this.projectDAO.loadEntityById(id);
        BeanUtilsBean merger = new NullAwareBeanUtilsBean("isMaster", "masterName");
        try {
            merger.copyProperties(project, new BeanMapper<>(ProjectDTO.class).apply(projectRecord));
        } catch (final Exception e) {
            throw new GlobalException(e, ErrorType.INTERNAL_ERROR, null);
        }

        this.projectDAO.save(project);
    }
}
