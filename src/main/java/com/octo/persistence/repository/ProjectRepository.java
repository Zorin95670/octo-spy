package com.octo.persistence.repository;

import com.octo.persistence.model.Project;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DAO for project entity.
 *
 * @author Vincent Moittié
 */
@Repository
public interface ProjectRepository extends CrudRepository<Project, Long>,
        JpaSpecificationExecutor<Project> {

    /**
     * Find project by name.
     *
     * @param name Name to find.
     * @return Optional of project.
     */
    Optional<Project> findByName(String name);
}
