package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.octo.dao.CommonDAO;
import com.octo.utils.predicate.filter.QueryFilter;
import com.octo.model.entity.DeploymentProgress;

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
