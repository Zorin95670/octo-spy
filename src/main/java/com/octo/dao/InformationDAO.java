package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.octo.model.entity.InformationView;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * DAO for information entity.
 *
 * @author Vincent Moittié
 *
 */
@Repository("informationDAO")
public class InformationDAO extends CommonDAO<InformationView, QueryFilter> {

    /**
     * Default constructor.
     */
    public InformationDAO() {
        super(InformationView.class);
    }

}
