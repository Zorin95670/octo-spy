package com.octo.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import com.octo.utils.predicate.PredicateMapper;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * Default methods to access database.
 *
 * @param <T>
 *            Entity.
 * @param <Y>
 *            Entity filter.
 *
 * @author Vincent Moittié
 *
 */
public interface IDAO<T, Y extends QueryFilter> {

    /**
     * Save or update entity in database.
     *
     * @param entity
     *            Entity to save
     * @return Entity with generated id.
     */
    T save(T entity);

    /**
     * Get Entity manager.
     *
     * @return Entity manager.
     */
    EntityManager getEntityManager();

    /**
     * Get entity.
     *
     * @param filter
     *            Entity filter.
     * @return Searched entity.
     */
    Optional<T> load(Y filter);

    /**
     * Get entity with lock.
     *
     * @param filter
     *            Entity filter.
     * @return Searched entity.
     */
    Optional<T> loadWithLock(Y filter);

    /**
     * Load entity by id.
     *
     * @param id
     *            Id of entity.
     * @return Entity.
     */
    T loadById(Object id);

    /**
     * Load entity by id with lock.
     *
     * @param id
     *            Id of entity.
     * @return Entity.
     */
    T loadByIdWithLock(Object id);

    /**
     * Load entity by id, throw exception on bad id.
     *
     * @param id
     *            Id of entity.
     * @return Entity.
     */
    T loadEntityById(Object id);

    /**
     * Load entity by id, throw exception on bad id.
     *
     * @param id
     *            Id of entity.
     * @param fieldName
     *            Name of field to precise in exception.
     * @return Entity.
     */
    T loadEntityById(Object id, String fieldName);

    /**
     * Load entity by id with lock, throw exception on bad id.
     *
     * @param id
     *            Id of entity.
     * @return Entity.
     */
    T loadEntityByIdWithLock(Object id);

    /**
     * Load entity by id with lock, throw exception on bad id.
     *
     * @param id
     *            Id of entity.
     * @param fieldName
     *            Name of field to precise in exception.
     * @return Entity.
     */
    T loadEntityByIdWithLock(Object id, String fieldName);

    /**
     * Delete entity in database.
     *
     * @param entity
     *            Entity to delete.
     */
    void delete(T entity);

    /**
     * Delete All using criteria.
     *
     * @param filter
     *            Entity filter.
     * @return The number of entities deleted.
     */
    int deleteAll(Y filter);

    /**
     * Count total of entity.
     *
     * @param filter
     *            Entity filter.
     * @return Total of entity.
     */
    Long count(Y filter);

    /**
     * Get all entities.
     *
     * @param filter
     *            Entity filter.
     * @return All entities.
     */
    List<T> find(Y filter);

    /**
     * Get all entities with lock.
     *
     * @param filter
     *            Entity filter.
     * @return All entities.
     */
    List<T> findWithLock(Y filter);

    /**
     * Get all entities.
     *
     * @param filter
     *            Entity filter.
     * @param ignorePagination
     *            Indicate whether pagination is ignored.
     * @return All entities.
     */
    List<T> find(Y filter, Boolean ignorePagination);

    /**
     * Get all entities with lock.
     *
     * @param filter
     *            Entity filter.
     * @param ignorePagination
     *            Indicate whether pagination is ignored.
     * @return All entities.
     */
    List<T> findWithLock(Y filter, Boolean ignorePagination);

    /**
     * Get all entities with lock.
     *
     * @param filter
     *            Entity filter.
     * @param ignorePagination
     *            Indicate whether pagination is ignored.
     * @param lock
     *            Lock to apply.
     * @return All entities.
     */
    List<T> findWithLock(Y filter, Boolean ignorePagination, LockModeType lock);

    /**
     * Get all entities with lock.
     *
     * @param filter
     *            Entity filter.
     * @param ignorePagination
     *            Indicate whether pagination is ignored.
     * @param lock
     *            Lock to apply.
     * @param mapper
     *            Mapper to get predicates from filter.
     * @return All entities.
     */
    List<T> findWithLock(Y filter, Boolean ignorePagination, LockModeType lock, PredicateMapper<T, QueryFilter> mapper);

    /**
     * Get entity class.
     *
     * @return Entity class.
     */
    Class<T> getType();

    /**
     * Update property of an entity.
     *
     * @param filter
     *            Entity filter.
     * @param name
     *            Name of property to update.
     * @param value
     *            Value to set.
     * @return The number of entities updated.
     */
    int updateProperty(Y filter, String name, Object value);

    /**
     * Update property of an entity.
     *
     * @param filter
     *            Entity filter.
     * @param name
     *            Name of property to update.
     * @param value
     *            Value to set.
     * @param mapper
     *            Mapper to get predicates from filter.
     * @return The number of entities updated.
     */
    int updateProperty(Y filter, String name, Object value, PredicateMapper<T, QueryFilter> mapper);
    /**
     * Execute update procedure.
     *
     * @param procedure
     *            Procedure name.
     * @param parameters
     *            Parameters of procedure.
     * @return The number of entities updated.
     */
    int callUpdateProcedure(String procedure, ProcedureParameter... parameters);
}
