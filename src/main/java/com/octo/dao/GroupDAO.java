package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.octo.dao.CommonDAO;
import com.octo.utils.predicate.filter.QueryFilter;
import com.octo.model.entity.Group;

/**
 * DAO for group entity.
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
