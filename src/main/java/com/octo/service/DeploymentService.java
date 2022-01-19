package com.octo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.octo.model.deployment.DeploymentRecord;
import com.octo.persistence.model.Deployment;
import com.octo.persistence.model.DeploymentView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Deployment service.
 */
public interface DeploymentService extends ServiceHelper {

    /**
     * Count deployments.
     *
     * @param filters Filter options.
     * @param field Field to count.
     * @param value Default value for count.
     * @return Count object.
     */
    JsonNode count(Map<String, String> filters, String field, String value);

    /**
     * Load Deployment by id.
     *
     * @param id Id.
     * @return Deployment.
     */
    Deployment load(Long id);

    /**
     * Load deployment view by id.
     *
     * @param id Id to find.
     * @return Deployment view.
     */
    DeploymentView loadView(Long id);

    /**
     * Save deployment in database.
     *
     * @param newDeployment Record to save
     * @return Deployment.
     */
    DeploymentView save(DeploymentRecord newDeployment);

    /**
     * Disable previous deployment.
     *
     * @param entity Entity to search previous deployment.
     */
    void disablePreviousDeployment(Deployment entity);

    /**
     * Delete an deployment.
     *
     * @param id Id of deployment to update.
     */
    void delete(Long id);

    /**
     * Delete progress of deployment.
     *
     * @param filters Filters.
     */
    void deleteProgressDeployment(Map<String, String> filters);

    /**
     * Delete all progress from a deployment with same project, environment and
     * client.
     *
     * @param deployment Deployment to identify progress.
     */
    void deleteAllProgress(Deployment deployment);

    /**
     * Find deployments.
     *
     * @param filters  Filters.
     * @param pageable Page option.
     * @return List of selected deployments.
     */
    Page<DeploymentView> find(Map<String, String> filters, Pageable pageable);

    /**
     * Update deployment.
     *
     * @param id               Deployment's id.
     * @param deploymentRecord Deployment's information.
     * @throws InvocationTargetException If the property accessor method throws an exception.
     * @throws IllegalAccessException    If the caller does not have access to the property accessor
     *                                   method.
     */
    void update(Long id, Deployment deploymentRecord) throws IllegalAccessException, InvocationTargetException;
}
