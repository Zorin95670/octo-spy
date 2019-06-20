package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.octo.model.entity.Environment;

@Repository("defaultDAO")
public class DefaultDAO extends CommonDAO<Environment> {

    public DefaultDAO() {
        super(Environment.class);
    }

}
