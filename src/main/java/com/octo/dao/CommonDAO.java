package com.octo.dao;

import java.util.List;
import java.util.function.BiFunction;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.octo.model.exception.OctoException;

/**
 * Default database operation.
 *
 * @param <T>
 *            Entity
 *
 * @author vmoittie
 *
 */
public abstract class CommonDAO<T> implements IDAO<T> {

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

    @Override
    public final Class<T> getType() {
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
     * Save entity in database.
     */
    @Override
    public T save(final T entity) throws OctoException {
        this.getEntityManager().persist(entity);
        return entity;
    }

    @Override
    public final T loadById(final Long id) throws OctoException {
        return this.getEntityManager().find(this.getType(), id);
    }

    @Override
    public final T load(final BiFunction<CriteriaBuilder, Root<T>, Predicate> predicate) throws OctoException {
        CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getType());
        Root<T> root = query.from(getType());
        query.select(root).where(predicate.apply(builder, root));

        try {
            return this.getEntityManager().createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * This methods throw an UnsupportedOperationException.
     */
    @Override
    public List<T> findAll() {
        throw new UnsupportedOperationException();
    }
}
