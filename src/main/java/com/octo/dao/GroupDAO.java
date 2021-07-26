package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.octo.model.entity.Group;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * DAO for group entity.
 *
 * @author Vincent Moittié
 *
 */
@Repository("groupDAO")
public class GroupDAO extends CommonDAO<Group, QueryFilter> {

    /**
     * Default constructor.
     */
    public GroupDAO() {
        super(Group.class);
    }

}
