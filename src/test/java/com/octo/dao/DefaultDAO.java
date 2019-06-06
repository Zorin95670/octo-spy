package com.octo.dao;

import com.octo.model.entity.Environment;

public class DefaultDAO extends CommonDAO<Environment> {

    public DefaultDAO() {
        super(Environment.class);
    }

}
