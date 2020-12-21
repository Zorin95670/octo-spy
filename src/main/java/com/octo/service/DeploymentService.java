package com.octo.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cji.dao.IDAO;
import com.cji.models.error.ErrorType;
import com.cji.models.error.GlobalException;
import com.cji.utils.bean.BeanMapper;
import com.cji.utils.predicate.filter.QueryFilter;
import com.octo.model.dto.common.SearchByNameDTO;
import com.octo.model.dto.deployment.DeploymentDTO;
import com.octo.model.dto.deployment.NewDeploymentDTO;
import com.octo.model.dto.deployment.SearchDeploymentDTO;
import com.octo.model.entity.Deployment;
import com.octo.model.entity.Environment;
import com.octo.model.entity.Project;
import com.octo.utils.Constants;

/**
 * Deployment service.
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
     * @param dto
     *            DTO to save
     * @return Deployment.
     */
    public DeploymentDTO save(final NewDeploymentDTO dto) {
        if (dto.getEnvironment() == null) {
            throw new GlobalException(ErrorType.EMPTY_VALUE, "environment", null);
        }
        if (dto.getProject() == null) {
            throw new GlobalException(ErrorType.EMPTY_VALUE, Constants.FIELD_PROJECT, null);
        }

        if (dto.getClient() == null) {
            throw new GlobalException(ErrorType.EMPTY_VALUE, "client", null);
        }

        if (dto.getVersion() == null) {
            throw new GlobalException(ErrorType.EMPTY_VALUE, "version", null);
        }

        Optional<Environment> environment = this.environmentDAO.load(new SearchByNameDTO(dto.getEnvironment()));
        if (!environment.isPresent()) {
            throw new GlobalException(ErrorType.ENTITY_NOT_FOUND, Constants.FIELD_ENVIRONMENT, dto.getEnvironment());
        }

        Optional<Project> project = this.projectDAO.load(new SearchByNameDTO(dto.getProject()));
        if (!project.isPresent()) {
            throw new GlobalException(ErrorType.ENTITY_NOT_FOUND, Constants.FIELD_PROJECT, dto.getProject());
        }

        Deployment entity = new BeanMapper<>(Deployment.class, Constants.FIELD_ENVIRONMENT, Constants.FIELD_PROJECT)
                .apply(dto);
        entity.setEnvironment(environment.get());
        entity.setProject(project.get());

        if (entity.isAlive()) {
            this.disablePreviousDeployment(entity);
        }

        entity = this.deploymentDAO.save(entity);

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
}
