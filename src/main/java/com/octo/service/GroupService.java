package com.octo.service;

import com.octo.persistence.model.Group;
import com.octo.persistence.model.Project;
import com.octo.persistence.model.ProjectGroup;

/**
 * Implementation of group service.
 */
public interface GroupService extends ServiceHelper {

    /**
     * Create a group.
     *
     * @param project Project.
     * @return Group.
     */
    Group save(Project project);

    /**
     * Add project to a group.
     *
     * @param masterProjectId Master project id to find the group.
     * @param project         Project to save.
     * @return Project group.
     */
    ProjectGroup addProjectToGroup(Long masterProjectId, Project project);
}
