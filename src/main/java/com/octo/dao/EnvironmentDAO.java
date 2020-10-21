package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.cji.dao.CommonDAO;
import com.cji.utils.predicate.filter.QueryFilter;
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
