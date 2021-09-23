package com.octo.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.octo.dao.IDAO;
import com.octo.model.dto.group.SearchGroupDTO;
import com.octo.model.entity.Group;
import com.octo.model.entity.Project;
import com.octo.model.entity.ProjectGroup;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * Implementation of group service.
 *
 * @author Vincent Moittié
 *
 */
@Service
@Transactional
public class GroupService implements IGroupService {

    /**
     * Group's DAO.
     */
    @Autowired
    private IDAO<Group, QueryFilter> groupDAO;

    /**
     * Project group's DAO.
     */
    @Autowired
    private IDAO<ProjectGroup, QueryFilter> projectGroupDAO;

    @Override
    public final Group create(final Project project) {
        Group entity = new Group();
        entity.setMasterProject(project);
        entity = groupDAO.save(entity);

        ProjectGroup projectGroup = new ProjectGroup();
        projectGroup.setGroup(entity);
        projectGroup.setProject(project);

        projectGroupDAO.save(projectGroup);

        return entity;
    }

    @Override
    public final ProjectGroup addProjectToGroup(final Long masterProjectId, final Project project) {
        SearchGroupDTO groupFilter = new SearchGroupDTO();
        groupFilter.setMasterProject(masterProjectId.toString());

        Optional<Group> group = groupDAO.load(groupFilter);

        if (group.isEmpty()) {
            throw new GlobalException(ErrorType.ENTITY_NOT_FOUND, "group");
        }

        ProjectGroup projectGroup = new ProjectGroup();
        projectGroup.setGroup(group.get());
        projectGroup.setProject(project);

        return projectGroupDAO.save(projectGroup);
    }
}
