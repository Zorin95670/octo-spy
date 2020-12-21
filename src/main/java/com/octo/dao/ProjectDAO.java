package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.cji.dao.CommonDAO;
import com.cji.utils.predicate.filter.QueryFilter;
import com.octo.model.entity.Project;

/**
 * DAO for project entity.
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
