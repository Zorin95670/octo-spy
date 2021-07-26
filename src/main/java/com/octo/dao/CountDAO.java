package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.octo.model.dto.count.CountDTO;

/**
 * Count DAO, to count object in database.
 *
 * @author Vincent Moittié
 *
 */
@Repository("countDAO")
public class CountDAO extends CommonDAO<Object[], CountDTO> {

    /**
     * Default constructor.
     */
    public CountDAO() {
        super(null);
    }
}
