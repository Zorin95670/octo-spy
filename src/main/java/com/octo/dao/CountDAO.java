package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.octo.models.dto.count.CountDTO;

/**
 * Count DAO, to count object in database.
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
