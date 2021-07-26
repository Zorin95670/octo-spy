package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.octo.model.entity.Environment;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * Environment DAO.
 *
 * @author Vincent Moittié
 *
 */
@Repository("environmentDAO")
public class EnvironmentDAO extends CommonDAO<Environment, QueryFilter> {

    /**
     * Constructor for Environment's DAO.
     */
    public EnvironmentDAO() {
        super(Environment.class);
    }
}
