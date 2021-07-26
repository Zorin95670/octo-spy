package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.octo.dao.CommonDAO;
import com.octo.utils.predicate.filter.QueryFilter;
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
