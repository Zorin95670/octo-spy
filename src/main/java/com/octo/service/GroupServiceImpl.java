package com.octo.service;

import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.persistence.model.Group;
import com.octo.persistence.model.Project;
import com.octo.persistence.model.ProjectGroup;
import com.octo.persistence.repository.GroupRepository;
import com.octo.persistence.repository.ProjectGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Implementation of group service.
 *
 * @author Vincent Moittié
 */
@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    /**
     * Group's repository.
     */
    @Autowired
    private GroupRepository groupRepository;

    /**
     * Project group's repository.
     */
    @Autowired
    private ProjectGroupRepository projectGroupRepository;

    @Override
    public Group save(final Project project) {
        Group entity = new Group();
        entity.setMasterProject(project);
        entity = groupRepository.save(entity);

        ProjectGroup projectGroup = new ProjectGroup();
        projectGroup.setGroup(entity);
        projectGroup.setProject(project);

        projectGroupRepository.save(projectGroup);
        return entity;
    }

    @Override
    public ProjectGroup addProjectToGroup(final Long masterProjectId, final Project project) {
        Optional<Group> group = groupRepository.findByMasterProjectId(masterProjectId);

        if (group.isEmpty()) {
            throw new GlobalException(ErrorType.ENTITY_NOT_FOUND, "group");
        }

        ProjectGroup projectGroup = new ProjectGroup();
        projectGroup.setGroup(group.get());
        projectGroup.setProject(project);

        return projectGroupRepository.save(projectGroup);
    }
}
