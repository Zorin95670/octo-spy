package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.cji.dao.CommonDAO;
import com.cji.utils.predicate.filter.QueryFilter;
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
