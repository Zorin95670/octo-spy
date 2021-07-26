package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.octo.model.entity.Deployment;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * DAO for deployment entity.
 *
 * @author Vincent Moittié
 *
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
