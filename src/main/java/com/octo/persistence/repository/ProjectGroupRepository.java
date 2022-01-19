package com.octo.persistence.repository;

import com.octo.persistence.model.ProjectGroup;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO for project group entity.
 *
 * @author Vincent Moittié
 */
@Repository
public interface ProjectGroupRepository extends CrudRepository<ProjectGroup, Long>,
        JpaSpecificationExecutor<ProjectGroup> {

}
