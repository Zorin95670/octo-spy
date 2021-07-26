package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.octo.model.entity.DeploymentProgress;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * DAO for progress of deployment entity.
 */
@Repository("deploymentProgressDAO")
public class DeploymentProgressDAO extends CommonDAO<DeploymentProgress, QueryFilter> {

    /**
     * Default constructor.
     */
    public DeploymentProgressDAO() {
        super(DeploymentProgress.class);
    }

}
