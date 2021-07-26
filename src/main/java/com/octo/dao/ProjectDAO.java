package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.octo.model.entity.Project;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * DAO for project entity.
 *
 * @author Vincent Moittié
 *
 */
@Repository("projectDAO")
public class ProjectDAO extends CommonDAO<Project, QueryFilter> {

    /**
     * Default constructor.
     */
    public ProjectDAO() {
        super(Project.class);
    }

}
