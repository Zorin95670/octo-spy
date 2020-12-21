package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.cji.dao.CommonDAO;
import com.cji.utils.predicate.filter.QueryFilter;
import com.octo.model.entity.Deployment;

/**
 * DAO for deployment entity.
 */
@Repository("deploymentDAO")
public class DeploymentDAO extends CommonDAO<Deployment, QueryFilter> {

    /**
     * Default constructor.
     */
    public DeploymentDAO() {
        super(Deployment.class);
    }

}
