package com.octo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.octo.dao.IDAO;
import com.octo.model.common.Resource;
import com.octo.model.dto.common.SearchByNameDTO;
import com.octo.model.dto.deployment.DeploymentDTO;
import com.octo.model.dto.deployment.NewDeploymentRecord;
import com.octo.model.dto.deployment.SearchDeploymentDTO;
import com.octo.model.dto.deployment.SearchDeploymentViewDTO;
import com.octo.model.dto.deployment.SearchProgressDeploymentDTO;
import com.octo.model.entity.Deployment;
import com.octo.model.entity.DeploymentProgress;
import com.octo.model.entity.DeploymentView;
import com.octo.model.entity.Environment;
import com.octo.model.entity.Project;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.utils.Configuration;
import com.octo.utils.Constants;
import com.octo.utils.bean.BeanMapper;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * Deployment service.
 *
 * @author Vincent Moittié
 *
 */
@Service
@Transactional
public class DeploymentService {

    /**
     * Deployment's DAO.
     */
    @Autowired
    private IDAO<Deployment, QueryFilter> deploymentDAO;

    /**
     * Deployment's DAO.
     */
    @Autowired
    private IDAO<DeploymentView, QueryFilter> deploymentViewDAO;

    /**
     * Progress of deployment's DAO.
     */
    @Autowired
    private IDAO<DeploymentProgress, QueryFilter> deploymentProgressDAO;

    /**
     * Environment's DAO.
     */
    @Autowired
    private IDAO<Environment, QueryFilter> environmentDAO;

    /**
     * Project's DAO.
     */
    @Autowired
    private IDAO<Project, QueryFilter> projectDAO;

    /**
     * Configuration class.
     */
    @Autowired
    private Configuration configuration;

    /**
     * Load Deployment by id.
     *
     * @param id
     *            Id.
     * @return Deployment.
     */
    public DeploymentDTO load(final Long id) {
        return new BeanMapper<>(DeploymentDTO.class).apply(this.deploymentDAO.loadEntityById(id));
    }

    /**
     * Save deployment in database.
     *
     * @param newDeployment
     *            Record to save
     * @return Deployment.
     */
    public DeploymentDTO save(final NewDeploymentRecord newDeployment) {
        if (newDeployment.environment() == null) {
            throw new GlobalException(ErrorType.EMPTY_VALUE, Constants.FIELD_ENVIRONMENT, null);
        }
        if (newDeployment.project() == null) {
            throw new GlobalException(ErrorType.EMPTY_VALUE, Constants.FIELD_PROJECT, null);
        }

        if (newDeployment.client() == null) {
            throw new GlobalException(ErrorType.EMPTY_VALUE, "client", null);
        }

        if (newDeployment.version() == null) {
            throw new GlobalException(ErrorType.EMPTY_VALUE, "version", null);
        }

        Optional<Environment> environment = this.environmentDAO.load(new SearchByNameDTO(newDeployment.environment()));
        if (!environment.isPresent()) {
            throw new GlobalException(ErrorType.ENTITY_NOT_FOUND, Constants.FIELD_ENVIRONMENT, newDeployment.environment());
        }

        Optional<Project> project = this.projectDAO.load(new SearchByNameDTO(newDeployment.project()));
        if (!project.isPresent()) {
            throw new GlobalException(ErrorType.ENTITY_NOT_FOUND, Constants.FIELD_PROJECT, newDeployment.project());
        }

        Deployment entity = new BeanMapper<>(Deployment.class, Constants.FIELD_ENVIRONMENT, Constants.FIELD_PROJECT)
                .apply(newDeployment);
        entity.setEnvironment(environment.get());
        entity.setProject(project.get());

        if (entity.isAlive()) {
            this.disablePreviousDeployment(entity);
        }

        entity = this.deploymentDAO.save(entity);

        if (newDeployment.inProgress()) {
            DeploymentProgress progress = new DeploymentProgress();
            progress.setDeployment(entity);
            this.deploymentProgressDAO.save(progress);
        }

        return new BeanMapper<>(DeploymentDTO.class).apply(entity);
    }

    /**
     * Disable previous deployment.
     *
     * @param entity
     *            Entity to search previous deployment.
     */
    public void disablePreviousDeployment(final Deployment entity) {
        SearchDeploymentDTO searchDTO = new SearchDeploymentDTO();
        searchDTO.setAlive("true");
        searchDTO.setClient(entity.getClient());
        searchDTO.setEnvironment(entity.getEnvironment().getId().toString());
        searchDTO.setProject(entity.getProject().getId().toString());
        searchDTO.setCount(1);
        searchDTO.setOrder("insertDate");
        searchDTO.setSort("desc");

        Optional<Deployment> deployment = this.deploymentDAO.find(searchDTO).stream().findAny();

        if (!deployment.isPresent()) {
            return; // No entity to update.
        }
        Deployment entityToUpdate = deployment.get();
        entityToUpdate.setAlive(false);
        this.deploymentDAO.save(entityToUpdate);
    }

    /**
     * Delete an deployment.
     *
     * @param id
     *            Id of deployment to update.
     */
    public void delete(final Long id) {
        final Deployment entity = this.deploymentDAO.loadById(id);
        this.deploymentDAO.delete(entity);
    }

    /**
     * Delete progress of deployment.
     *
     * @param dto
     *            Filter.
     */
    public void deleteProgressDeployment(final SearchDeploymentDTO dto) {
        if (dto.getEnvironment() == null) {
            throw new GlobalException(ErrorType.EMPTY_VALUE, Constants.FIELD_ENVIRONMENT);
        }
        Optional<Environment> environment = this.environmentDAO.load(new SearchByNameDTO(dto.getEnvironment()));
        if (!environment.isPresent()) {
            throw new GlobalException(ErrorType.ENTITY_NOT_FOUND, Constants.FIELD_ENVIRONMENT);
        }
        if (dto.getProject() == null) {
            throw new GlobalException(ErrorType.EMPTY_VALUE, Constants.FIELD_PROJECT);
        }
        Optional<Project> project = this.projectDAO.load(new SearchByNameDTO(dto.getProject()));
        if (!project.isPresent()) {
            throw new GlobalException(ErrorType.ENTITY_NOT_FOUND, Constants.FIELD_PROJECT);
        }

        dto.setEnvironment(environment.get().getId().toString());
        dto.setProject(project.get().getId().toString());
        Optional<Deployment> deployment = this.deploymentDAO.load(dto);
        if (!deployment.isPresent()) {
            throw new GlobalException(ErrorType.ENTITY_NOT_FOUND, "deployment");
        }
        SearchProgressDeploymentDTO searchDTO = new SearchProgressDeploymentDTO();
        searchDTO.setDeployment(deployment.get().getId().toString());
        Optional<DeploymentProgress> progress = this.deploymentProgressDAO.load(searchDTO);

        if (!progress.isPresent()) {
            throw new GlobalException(ErrorType.ENTITY_NOT_FOUND, "deploymentProgress");
        }
        this.deploymentProgressDAO.delete(progress.get());
    }

    /**
     * Count deployments.
     *
     * @param dto
     *            Filter.
     * @return Number of selected deployments.
     */
    public Long count(final SearchDeploymentViewDTO dto) {
        return this.deploymentViewDAO.count(dto);
    }

    /**
     * Find deployments.
     *
     * @param dto
     *            Filter.
     * @return List of selected deployments.
     */
    public Resource<DeploymentDTO> find(final SearchDeploymentViewDTO dto) {
        if (dto.getPage() < 0) {
            throw new GlobalException(ErrorType.WRONG_VALUE, "page", Integer.toString(dto.getPage()));
        }

        if (dto.getCount() <= 0) {
            dto.setCount(this.configuration.getDefaultApiLimit());
        } else if (dto.getCount() > this.configuration.getMaximumApiLimit()) {
            dto.setCount(this.configuration.getMaximumApiLimit());
        }

        final Long total = this.count(dto);
        final List<DeploymentDTO> deployments = this.deploymentViewDAO.find(dto).stream()
                .map(new BeanMapper<>(DeploymentDTO.class)).collect(Collectors.toList());

        return new Resource<>(total, deployments, dto.getPage(), dto.getCount());
    }
}
