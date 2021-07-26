package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.octo.dao.CommonDAO;
import com.octo.utils.predicate.filter.QueryFilter;
import com.octo.model.entity.DeploymentView;

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
