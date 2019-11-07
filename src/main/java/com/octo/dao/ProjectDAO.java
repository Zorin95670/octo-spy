package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.octo.model.dto.common.DefaultDTO;
import com.octo.model.entity.Project;

/**
 * DAO for project entity.
 *
 * @author vmoittie
 *
 */
@Repository("projectDAO")
public class ProjectDAO extends CommonDAO<Project, DefaultDTO> {

    /**
     * Default constructor.
     */
    public ProjectDAO() {
        super(Project.class);
    }

}
