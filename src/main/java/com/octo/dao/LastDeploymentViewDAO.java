package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.octo.dao.CommonDAO;
import com.octo.utils.predicate.filter.QueryFilter;
import com.octo.model.entity.LastDeploymentView;

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
