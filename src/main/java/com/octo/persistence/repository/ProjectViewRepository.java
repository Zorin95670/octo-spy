package com.octo.persistence.repository;

import com.octo.persistence.model.ProjectView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DAO for project view entity.
 *
 * @author Vincent Moittié
 */
@Repository
public interface ProjectViewRepository extends CrudRepository<ProjectView, Long>,
        JpaSpecificationExecutor<ProjectView>, CountRepository<ProjectView> {

    /**
     * Find master project by name.
     *
     * @param name Name to find.
     * @return Optional of project.
     */
    Optional<ProjectView> findByNameAndIsMasterIsTrue(String name);

    /**
     * Find subproject by name and master project name.
     *
     * @param name          Name to find.
     * @param masterProject Master project name to find.
     * @return Optional of project.
     */
    Optional<ProjectView> findByNameAndMasterProjectAndIsMasterIsFalse(String name, String masterProject);

    /**
     * Get all projects.
     *
     * @param specification Filter options.
     * @param pageable      Page options.
     * @return List of project.
     */
    Page<ProjectView> findAll(Specification<ProjectView> specification, Pageable pageable);
}
