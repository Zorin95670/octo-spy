package com.octo.service;

import com.octo.model.entity.Group;
import com.octo.model.entity.Project;
import com.octo.model.entity.ProjectGroup;

/**
 * Group service.
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
     * @param masterProject
     *            Master project used to identify group.
     * @param project
     *            Project to add in group
     * @return Created ProjectGroup.
     */
    ProjectGroup addProjectToGroup(Project masterProject, Project project);
}
