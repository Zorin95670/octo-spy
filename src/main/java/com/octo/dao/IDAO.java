package com.octo.dao;

import java.util.List;

/**
 * Default methods to access database.
 *
 * @param <T>
 *            Entity.
 *
 * @author vmoittie
 */
public interface IDAO<T> {

    /**
     * Get all entity.
     *
     * @return All entity.
     */
    List<T> findAll();
}
