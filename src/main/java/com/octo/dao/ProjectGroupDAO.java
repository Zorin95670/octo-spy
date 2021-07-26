package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.octo.model.entity.ProjectGroup;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * DAO for project group entity.
 */
@Repository("projectGroupDAO")
public class ProjectGroupDAO extends CommonDAO<ProjectGroup, QueryFilter> {

    /**
     * Default constructor.
     */
    public ProjectGroupDAO() {
        super(ProjectGroup.class);
    }

}
