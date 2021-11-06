package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.octo.model.dto.count.MultipleCountDTO;

/**
 * Count DAO, to count object in database.
 *
 * @author Vincent Moittié
 *
 */
@Repository("multipleCountDAO")
public class MultipleCountDAO extends CommonDAO<Object[], MultipleCountDTO> {

    /**
     * Default constructor.
     */
    public MultipleCountDAO() {
        super(null);
    }
}
