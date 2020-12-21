package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.cji.dao.CommonDAO;
import com.cji.utils.predicate.filter.QueryFilter;
import com.octo.model.entity.DeploymentView;

/**
 * DAO for deployment entity.
 */
@Repository("deploymentViewDAO")
public class DeploymentViewDAO extends CommonDAO<DeploymentView, QueryFilter> {

    /**
     * Default constructor.
     */
    public DeploymentViewDAO() {
        super(DeploymentView.class);
    }
}
