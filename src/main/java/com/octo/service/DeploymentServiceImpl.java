package com.octo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.octo.model.common.Constants;
import com.octo.model.deployment.DeploymentRecord;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.persistence.model.Deployment;
import com.octo.persistence.model.DeploymentProgress;
import com.octo.persistence.model.DeploymentView;
import com.octo.persistence.model.Environment;
import com.octo.persistence.model.Project;
import com.octo.persistence.repository.DeploymentProgressRepository;
import com.octo.persistence.repository.DeploymentRepository;
import com.octo.persistence.repository.DeploymentViewRepository;
import com.octo.persistence.repository.EnvironmentRepository;
import com.octo.persistence.repository.ProjectRepository;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of deployment service.
 */
@Service
@Transactional
public class DeploymentServiceImpl implements DeploymentService {

    /**
     * Deployment's repository.
     */
    @Autowired
    private DeploymentRepository deploymentRepository;

    /**
     * Deployment view's repository.
     */
    @Autowired
    private DeploymentViewRepository deploymentViewRepository;

    /**
     * Progress of deployment's repository.
     */
    @Autowired
    private DeploymentProgressRepository deploymentProgressRepository;

    /**
     * Environment's repository.
     */
    @Autowired
    private EnvironmentRepository environmentRepository;

    /**
     * Project's repository.
     */
    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public JsonNode count(final Map<String, String> filters, final String field, final String value) {
        Specification<DeploymentView> specification = new SpecificationHelper<>(DeploymentView.class, filters);

        if (!new ClassHasFieldPredicate(DeploymentView.class).test(field)) {
            throw new GlobalException(ErrorType.UNKNOWN_FIELD, "field", field);
        }
        return deploymentViewRepository.count(DeploymentView.class, specification, field, value);
    }

    @Override
    public Deployment load(final Long id) {
        return this.loadEntityById(deploymentRepository, id);
    }

    @Override
    public DeploymentView loadView(final Long id) {
        return this.loadEntityById(deploymentViewRepository, id);
    }

    @Override
    public DeploymentView save(final DeploymentRecord newDeployment) {
        Environment environment = this.environmentRepository.findByName(newDeployment.environment())
                .orElseThrow(() -> new GlobalException(ErrorType.WRONG_VALUE, Constants.FIELD_ENVIRONMENT,
                        newDeployment.environment()));
        Project project = this.projectRepository.findByName(newDeployment.project()).orElseThrow(() ->
                new GlobalException(ErrorType.WRONG_VALUE, Constants.FIELD_PROJECT, newDeployment.project()));

        Deployment entity = new BeanMapper<>(Deployment.class, Constants.FIELD_ENVIRONMENT, Constants.FIELD_PROJECT)
                .apply(newDeployment);
        entity.setEnvironment(environment);
        entity.setProject(project);

        if (entity.isAlive()) {
            this.disablePreviousDeployment(entity);
        }

        entity = this.deploymentRepository.save(entity);

        if (newDeployment.inProgress()) {
            this.deleteAllProgress(entity);

            DeploymentProgress progress = new DeploymentProgress();
            progress.setDeployment(entity);
            this.deploymentProgressRepository.save(progress);
        }

        return deploymentViewRepository.getById(entity.getId());
    }

    @Override
    public void disablePreviousDeployment(final Deployment entity) {
        Map<String, String> filters = new HashMap<>();
        filters.put("alive", "true");
        filters.put("client", entity.getClient());
        filters.put(Constants.FIELD_ENVIRONMENT, entity.getEnvironment().getId().toString());
        filters.put(Constants.FIELD_PROJECT, entity.getProject().getId().toString());

        Optional<Deployment> deployment = this.deploymentRepository.findOne(new SpecificationHelper<>(Deployment.class,
                filters));

        if (deployment.isEmpty()) {
            return;
        }
        Deployment entityToUpdate = deployment.get();
        entityToUpdate.setAlive(false);
        this.deploymentRepository.save(entityToUpdate);
    }

    @Override
    public void delete(final Long id) {
        this.loadEntityById(deploymentRepository, id);
        this.deploymentRepository.deleteById(id);
    }

    @Override
    public void deleteProgressDeployment(final Map<String, String> filters) {
        if (!filters.containsKey(Constants.FIELD_ENVIRONMENT)
                || StringUtils.isBlank(filters.get(Constants.FIELD_ENVIRONMENT))) {
            throw new GlobalException(ErrorType.EMPTY_VALUE, Constants.FIELD_ENVIRONMENT);
        }
        if (!filters.containsKey(Constants.FIELD_PROJECT)
                || StringUtils.isBlank(filters.get(Constants.FIELD_PROJECT))) {
            throw new GlobalException(ErrorType.EMPTY_VALUE, Constants.FIELD_PROJECT);
        }

        DeploymentView deployment = this.deploymentViewRepository
                .findOne(new SpecificationHelper<>(DeploymentView.class, filters))
                .orElseThrow(() -> new GlobalException(ErrorType.ENTITY_NOT_FOUND, "deployment"));

        DeploymentProgress progress =
                this.deploymentProgressRepository.findByDeploymentId(deployment.getId())
                        .orElseThrow(() -> new GlobalException(ErrorType.ENTITY_NOT_FOUND, "deploymentProgress"));

        this.deploymentProgressRepository.delete(progress);
    }

    @Override
    public void deleteAllProgress(final Deployment deployment) {
        this.deploymentProgressRepository
                .deleteAllProgress(deployment.getProject().getName(),
                        deployment.getEnvironment().getName(), deployment.getClient());
    }

    @Override
    public Page<DeploymentView> find(final Map<String, String> filters, final Pageable pageable) {
        return this.deploymentViewRepository
                .findAll(new SpecificationHelper<>(DeploymentView.class, filters), pageable);
    }

    @Override
    public void update(final Long id, final Deployment deploymentRecord)
            throws IllegalAccessException, InvocationTargetException {
        Deployment deployment = this.loadEntityById(deploymentRepository, id);
        BeanUtilsBean merger = new NullAwareBeanUtilsBean("id", Constants.FIELD_PROJECT,
                Constants.FIELD_ENVIRONMENT,
                "client");
        merger.copyProperties(deployment, new BeanMapper<>(Deployment.class).apply(deploymentRecord));

        this.deploymentRepository.save(deployment);
    }
}
