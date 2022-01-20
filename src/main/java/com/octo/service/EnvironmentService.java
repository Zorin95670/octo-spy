package com.octo.service;

import com.octo.model.environment.EnvironmentRecord;
import com.octo.persistence.model.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Environment service.
 */
public interface EnvironmentService extends ServiceHelper {

    /**
     * Get all environment.
     *
     * @param filters Filter options.
     * @param pageable Object to manage page and sort.
     * @return List of environment.
     */
    Page<Environment> findAll(Map<String, String> filters, Pageable pageable);

    /**
     * Save environment in database.
     *
     * @param newEnvironment Environment to save
     * @return Environment.
     */
    Environment save(EnvironmentRecord newEnvironment);

    /**
     * Update environment information.
     *
     * @param id             Environment's id.
     * @param newEnvironment Environment's information.
     */
    void update(Long id, EnvironmentRecord newEnvironment);

    /**
     * Delete an environment.
     *
     * @param id Id of environment.
     */
    void delete(Long id);
}
