package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.octo.model.entity.User;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * DAO for user entity.
 */
@Repository("userDAO")
public class UserDAO extends CommonDAO<User, QueryFilter> {

    /**
     * Default constructor.
     */
    public UserDAO() {
        super(User.class);
    }

}
