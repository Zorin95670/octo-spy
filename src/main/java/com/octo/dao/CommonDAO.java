package com.octo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * Default database operation.
 *
 * @param <T>
 *            Entity
 *
 * @author vmoittie
 *
 */
@Transactional
public class CommonDAO<T> implements IDAO<T> {

    /**
     * Entity manager.
     */
    @PersistenceContext
    private EntityManager entityManager;
    /**
     * Entity type.
     */
    private Class<T> type;

    /**
     * Create default DAO for specific entity type.
     *
     * @param clazz
     *            Entity type.
     */
    public CommonDAO(final Class<T> clazz) {
        this.type = clazz;
    }

    /**
     * Get entity type.
     *
     * @return Entity type.
     */
    public Class<T> getType() {
        return type;
    }

    /**
     * Get entity manager.
     *
     * @return Entity manager.
     */
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    /**
     * This methods throw an UnsupportedOperationException.
     */
    @Override
    public List<T> findAll() {
        throw new UnsupportedOperationException();
    }
}
