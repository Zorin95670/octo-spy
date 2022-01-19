package com.octo.persistence.repository;

import com.octo.persistence.model.DeploymentReportView;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO for deployment entity.
 *
 * @author Vincent Moittié
 */
@Repository
public interface DeploymentReportViewRepository extends CrudRepository<DeploymentReportView, Long>,
        JpaSpecificationExecutor<DeploymentReportView>, CountRepository<DeploymentReportView> {
}
