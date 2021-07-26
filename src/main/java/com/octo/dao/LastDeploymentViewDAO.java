package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.octo.model.entity.LastDeploymentView;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * DAO for last deployment entity.
 */
@Repository("lastDeploymentViewDAO")
public class LastDeploymentViewDAO extends CommonDAO<LastDeploymentView, QueryFilter> {

    /**
     * Default constructor.
     */
    public LastDeploymentViewDAO() {
        super(LastDeploymentView.class);
    }
}
