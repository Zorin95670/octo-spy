package com.octo.service;

import com.octo.model.entity.Group;
import com.octo.model.entity.Project;
import com.octo.model.entity.ProjectGroup;

/**
 * Group service.
 *
 * @author Vincent Moittié
 *
 */
public interface IGroupService {

    /**
     * Create new group with master.
     *
     * @param project
     *            Master's project.
     * @return Created group.
     */
    Group create(Project project);

    /**
     * Add project to group.
     *
     * @param masterProjectId
     *            Master project id.
     * @param project
     *            Project to add in group
     * @return Created ProjectGroup.
     */
    ProjectGroup addProjectToGroup(Long masterProjectId, Project project);
}
