package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.cji.dao.CommonDAO;
import com.cji.utils.predicate.filter.QueryFilter;
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
