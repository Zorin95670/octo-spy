package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.octo.model.entity.ProjectView;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * DAO for project view entity.
 *
 * @author Vincent Moittié
 *
 */
@Repository("projectViewDAO")
public class ProjectViewDAO extends CommonDAO<ProjectView, QueryFilter> {

    /**
     * Default constructor.
     */
    public ProjectViewDAO() {
        super(ProjectView.class);
    }

}
