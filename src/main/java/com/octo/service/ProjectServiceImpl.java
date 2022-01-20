package com.octo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.octo.model.common.Constants;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.model.project.ProjectRecord;
import com.octo.persistence.model.Project;
import com.octo.persistence.model.ProjectView;
import com.octo.persistence.repository.ProjectRepository;
import com.octo.persistence.repository.ProjectViewRepository;
import com.octo.persistence.specification.SpecificationHelper;
import com.octo.utils.bean.BeanMapper;
import com.octo.utils.bean.NullAwareBeanUtilsBean;
import com.octo.utils.reflect.ClassHasFieldPredicate;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of project service.
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    /**
     * Project repository.
     */
    @Autowired
    private ProjectRepository projectRepository;

    /**
     * Project repository.
     */
    @Autowired
    private ProjectViewRepository projectViewRepository;

    /**
     * Group service.
     */
    @Autowired
    private GroupService groupService;

    @Override
    public JsonNode count(final Map<String, String> filters, final String field, final String value) {
        Specification<ProjectView> specification = new SpecificationHelper<>(ProjectView.class, filters);

        if (!new ClassHasFieldPredicate(ProjectView.class).test(field)) {
            throw new GlobalException(ErrorType.UNKNOWN_FIELD, "field", field);
        }
        return projectViewRepository.count(ProjectView.class, specification, field, value);
    }

    @Override
    public ProjectView load(final Long id) {
        return this.loadEntityById(projectViewRepository, id);
    }

    @Override
    public Project save(final ProjectRecord newProject) {
        if (newProject.isMaster()) {
            return this.saveMasterProject(newProject);
        }

        return this.saveSubProject(newProject);
    }

    @Override
    public Project saveMasterProject(final ProjectRecord newProject) {
        Optional<ProjectView> masterProject = this.projectViewRepository.findByNameAndIsMasterIsTrue(newProject.name());
        if (masterProject.isPresent()) {
            throw new GlobalException(ErrorType.WRONG_VALUE, "name", "duplicate");
        }

        Project entity = this.projectRepository.save(new BeanMapper<>(Project.class).apply(newProject));
        groupService.save(entity);

        return entity;
    }

    @Override
    public Project saveSubProject(final ProjectRecord newProject) {
        if (StringUtils.isBlank(newProject.masterName())) {
            throw new GlobalException(ErrorType.EMPTY_VALUE, "masterName");
        }

        Optional<ProjectView> project =
                this.projectViewRepository.findByNameAndMasterProjectAndIsMasterIsFalse(newProject.name(),
                        newProject.masterName());
        if (project.isPresent()) {
            throw new GlobalException(ErrorType.WRONG_VALUE, "name", "duplicate");
        }

        ProjectView masterProject = this.projectViewRepository.findByNameAndIsMasterIsTrue(newProject.masterName())
                .orElseThrow(() -> new GlobalException(ErrorType.ENTITY_NOT_FOUND, Constants.FIELD_PROJECT,
                        newProject.masterName()));

        Project entity = this.projectRepository.save(new BeanMapper<>(Project.class).apply(newProject));
        groupService.addProjectToGroup(masterProject.getId(), entity);

        return entity;
    }

    @Override
    public void delete(final Long id) {
        this.loadEntityById(projectRepository, id);
        this.projectRepository.deleteById(id);
    }

    @Override
    public Page<ProjectView> findAll(final Map<String, String> filters, final Pageable pageable) {
        return this.projectViewRepository.findAll(new SpecificationHelper<>(ProjectView.class, filters), pageable);
    }

    @Override
    public void update(final Long id, final Project newProject)
            throws InvocationTargetException, IllegalAccessException {
        Project project = this.loadEntityById(projectRepository, id);
        BeanUtilsBean merger = new NullAwareBeanUtilsBean("id", "name", "isMaster", "masterName");
        merger.copyProperties(project, new BeanMapper<>(Project.class).apply(newProject));
        this.projectRepository.save(project);
    }
}
