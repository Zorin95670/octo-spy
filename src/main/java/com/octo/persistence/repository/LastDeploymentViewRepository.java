package com.octo.persistence.repository;

import com.octo.persistence.model.LastDeploymentView;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO for last deployment entity.
 *
 * @author Vincent Moittié
 */
@Repository
public interface LastDeploymentViewRepository extends CrudRepository<LastDeploymentView, Long>,
        JpaSpecificationExecutor<LastDeploymentView> {
}
