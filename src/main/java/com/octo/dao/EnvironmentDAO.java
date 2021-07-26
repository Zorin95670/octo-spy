package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.octo.dao.CommonDAO;
import com.octo.utils.predicate.filter.QueryFilter;
import com.octo.model.entity.Environment;

/**
 * Environment DAO.
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
