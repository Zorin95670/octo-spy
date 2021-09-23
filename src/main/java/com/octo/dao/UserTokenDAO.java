package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.octo.model.entity.UserToken;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * DAO for user token entity.
 */
@Repository("userTokenDAO")
public class UserTokenDAO extends CommonDAO<UserToken, QueryFilter> {

    /**
     * Default constructor.
     */
    public UserTokenDAO() {
        super(UserToken.class);
    }

}
