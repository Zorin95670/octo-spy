package com.octo.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.octo.dao.IDAO;
import com.octo.dao.filter.common.EqualsFilter;
import com.octo.dao.filter.deployment.PreviousDeploymentFilter;
import com.octo.model.dto.common.DefaultDTO;
import com.octo.model.dto.deployment.DeploymentDTO;
import com.octo.model.dto.deployment.NewDeploymentDTO;
import com.octo.model.entity.Deployment;
import com.octo.model.entity.Environment;
import com.octo.model.entity.Project;
import com.octo.model.error.ErrorType;
import com.octo.model.exception.OctoException;
import com.octo.utils.mapper.deployment.DeploymentDTOMapper;
import com.octo.utils.mapper.deployment.DeploymentEntityMapper;

/**
 * Deployment service.
 *
 * @author vmoittie
 *
 */
@Service
@Transactional
public class DeploymentService {

    /**
     * Deployment's DAO.
     */
    @Autowired
    private IDAO<Deployment, DefaultDTO> deploymentDAO;

    /**
     * Environment's DAO.
     */
    @Autowired
    private IDAO<Environment, DefaultDTO> environmentDAO;

    /**
     * Project's DAO.
     */
    @Autowired
    private IDAO<Project, DefaultDTO> projectDAO;

    /**
     * Load Deployment by id.
     *
     * @param id
     *            Id.
     * @return Deployment.
     * @throws OctoException
     *             On all database error.
     */
    public DeploymentDTO loadById(final Long id) {
        if (id == null) {
            throw new OctoException(ErrorType.EMPTY_VALUE, "id", null);
        }

        Deployment entity = this.deploymentDAO.loadById(id);

        if (entity == null) {
            throw new OctoException(ErrorType.ENTITY_NOT_FOUND, "id", id.toString());
        }

        return new DeploymentDTOMapper().apply(entity);
    }

    /**
     * Save deployment in database.
     *
     * @param dto
     *            DTO to save
     * @return Deployment.
     * @throws OctoException
     *             On all database error.
     */
    public DeploymentDTO save(final NewDeploymentDTO dto) {
        if (dto.getEnvironment() == null) {
            throw new OctoException(ErrorType.EMPTY_VALUE, "environment", null);
        }
        if (dto.getProject() == null) {
            throw new OctoException(ErrorType.EMPTY_VALUE, "project", null);
        }

        if (dto.getClient() == null) {
            throw new OctoException(ErrorType.EMPTY_VALUE, "client", null);
        }

        if (dto.getVersion() == null) {
            throw new OctoException(ErrorType.EMPTY_VALUE, "version", null);
        }

        Environment environment = this.environmentDAO.load(new EqualsFilter<Environment>("name", dto.getEnvironment()));

        if (environment == null) {
            throw new OctoException(ErrorType.BAD_VALUE, "environment", dto.getEnvironment());
        }

        Project project = this.projectDAO.load(new EqualsFilter<Project>("name", dto.getProject()));

        if (project == null) {
            throw new OctoException(ErrorType.BAD_VALUE, "project", dto.getProject());
        }

        Deployment entity = new DeploymentEntityMapper().apply(dto);
        entity.setEnvironment(environment);
        entity.setProject(project);
        entity.setAlive(dto.isAlive());

        if (entity.isAlive()) {
            this.disablePreviousDeployment(entity);
        }

        entity = this.deploymentDAO.save(entity);

        return new DeploymentDTOMapper().apply(entity);
    }

    /**
     * Disable previous deployment.
     *
     * @param entity
     *            Entity to search previous deployment.
     */
    public void disablePreviousDeployment(final Deployment entity) {
        Deployment entityToUpdate = this.deploymentDAO.load(new PreviousDeploymentFilter(entity));

        if (entityToUpdate == null) {
            return; // No entity to update.
        }

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
