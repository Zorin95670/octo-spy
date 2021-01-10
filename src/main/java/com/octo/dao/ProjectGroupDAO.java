package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.cji.dao.CommonDAO;
import com.cji.utils.predicate.filter.QueryFilter;
import com.octo.model.entity.ProjectGroup;

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
