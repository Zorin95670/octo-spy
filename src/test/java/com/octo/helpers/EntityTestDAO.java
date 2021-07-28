package com.octo.helpers;

import org.springframework.stereotype.Repository;

import com.octo.dao.CommonDAO;
import com.octo.utils.predicate.filter.QueryFilter;

@Repository("dao")
public class EntityTestDAO extends CommonDAO<EntityHelpers, QueryFilter> {

    public EntityTestDAO() {
        super(EntityHelpers.class);
    }
}
