package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.octo.model.entity.DeploymentView;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * DAO for deployment entity.
 */
@Repository("DeploymentViewDAO")
public class DeploymentViewDAO extends CommonDAO<DeploymentView, QueryFilter> {

    /**
     * Default constructor.
     */
    public DeploymentViewDAO() {
        super(DeploymentView.class);
    }
}
