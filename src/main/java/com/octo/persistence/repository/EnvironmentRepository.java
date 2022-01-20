package com.octo.persistence.repository;

import com.octo.persistence.model.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Environment DAO.
 *
 * @author Vincent Moittié
 */
@Repository
public interface EnvironmentRepository extends CrudRepository<Environment, Long>,
        JpaSpecificationExecutor<Environment> {

    /**
     * Find environment by name.
     *
     * @param name Name to find.
     * @return Optional of environment.
     */
    Optional<Environment> findByName(String name);

    /**
     * Find environment by name without matching case.
     *
     * @param name Name to find
     * @return Optional of environment.
     */
    Optional<Environment> findByNameIgnoreCase(String name);

    /**
     * Find environment by name without matching case and when environment is not the specified.
     *
     * @param name Name to find.
     * @param id Id to exclude.
     * @return Optional of environment.
     */
    Optional<Environment> findByNameIgnoreCaseAndIdIsNot(String name, Long id);

    /**
     * Find all environments.
     *
     * @param specification Filter options.
     * @param pageable Page options.
     * @return List of environments.
     */
    Page<Environment> findAll(Specification<Environment> specification, Pageable pageable);
}
