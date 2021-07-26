package com.octo.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.octo.models.error.ErrorType;
import com.octo.models.error.GlobalException;
import com.octo.utils.predicate.PredicateMapper;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * Default database operation.
 *
 * @param <T>
 *            Entity
 * @param <Y>
 *            EntityDTO
 */
public abstract class CommonDAO<T, Y extends QueryFilter> implements IDAO<T, Y> {

    /**
     * Entity manager.
     */
    @PersistenceContext
    private EntityManager entityManager;
    /**
     * Entity type.
     */
    private final Class<T> type;

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
        return this.type;
    }

    @Override
    public final EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public final T save(final T entity) {
        this.getEntityManager().persist(entity);
        return entity;
    }

    @Override
    public final Optional<T> loadWithLock(final Y filter) {
        if (filter == null) {
            return Optional.empty();
        }
        filter.setCount(1);
        List<T> entities = this.findWithLock(filter);
        return entities.stream().findFirst();
    }

    @Override
    public final Optional<T> load(final Y filter) {
        if (filter == null) {
            return Optional.empty();
        }
        filter.setCount(1);
        List<T> entities = this.find(filter);
        return entities.stream().findFirst();
    }

    @Override
    public final T loadById(final Object id) {
        return this.getEntityManager().find(this.getType(), id);
    }

    @Override
    public final T loadByIdWithLock(final Object id) {
        return this.getEntityManager().find(this.getType(), id, LockModeType.PESSIMISTIC_FORCE_INCREMENT);
    }

    /**
     * Method used to prevent code duplication when loading an entity by its ID, with or without an optimistic lock.
     * Throws exception when entity doesn't exist.
     *
     * @param id
     *            Id of entity.
     * @param withLock
     *            Indicate if lock is used.
     * @param fieldName
     *            Name of field to precise in exception.
     * @return Entity.
     */
    private T loadEntityById(final Object id, final Boolean withLock, final String fieldName) {
        if (id == null) {
            throw new GlobalException(ErrorType.EMPTY_VALUE, fieldName);
        }

        T entity;
        if (withLock) {
            entity = this.loadByIdWithLock(id);
        } else {
            entity = this.loadById(id);
        }

        if (entity == null) {
            throw new GlobalException(ErrorType.ENTITY_NOT_FOUND, fieldName, String.valueOf(id));
        }

        return entity;
    }

    @Override
    public final T loadEntityById(final Object id) {
        return loadEntityById(id, "id");
    }

    @Override
    public final T loadEntityById(final Object id, final String fieldName) {
        return loadEntityById(id, false, fieldName);
    }

    @Override
    public final T loadEntityByIdWithLock(final Object id) {
        return loadEntityByIdWithLock(id, "id");
    }

    @Override
    public final T loadEntityByIdWithLock(final Object id, final String fieldName) {
        return loadEntityById(id, true, fieldName);
    }

    @Override
    public final void delete(final T entity) {
        this.getEntityManager().remove(entity);
    }

    @Override
    public final int deleteAll(final Y filter) {
        if (filter == null) {
            throw new GlobalException(ErrorType.DELETE_WITHOUT_WHERE, "filter");
        }
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaDelete<T> criteria = builder.createCriteriaDelete(getType());
        final Root<T> root = criteria.from(this.getType());

        final Predicate[] predicates = new PredicateMapper<>(getType(), filter, criteria).apply(builder, root);

        // Preventing a deletion without a where clause.
        if (predicates.length == 0) {
            throw new GlobalException(ErrorType.DELETE_WITHOUT_WHERE, "predicates");
        }

        criteria.where(predicates);

        return this.getEntityManager().createQuery(criteria).executeUpdate();
    }

    @Override
    public final Long count(final Y filter) {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        final Root<T> root = criteria.from(this.getType());

        criteria.select(builder.count(root));

        if (filter != null) {
            criteria.where(new PredicateMapper<>(getType(), filter, criteria).apply(builder, root));
        }

        final TypedQuery<Long> query = this.getEntityManager().createQuery(criteria);

        return query.getSingleResult();

    }

    @Override
    public final List<T> find(final Y entity) {
        return findWithLock(entity, false, null);
    }

    @Override
    public final List<T> find(final Y entity, final Boolean ignorePagination) {
        return this.findWithLock(entity, ignorePagination, null);
    }

    @Override
    public final List<T> findWithLock(final Y entity) {
        return findWithLock(entity, false);
    }

    @Override
    public final List<T> findWithLock(final Y entity, final Boolean ignorePagination) {
        return this.findWithLock(entity, ignorePagination, LockModeType.PESSIMISTIC_FORCE_INCREMENT);
    }

    @Override
    public final List<T> findWithLock(final Y filter, final Boolean ignorePagination, final LockModeType lock) {
        PredicateMapper<T, QueryFilter> mapper = null;
        if (filter != null) {
            mapper = new PredicateMapper<>(getType(), filter);
        }
        return this.findWithLock(filter, ignorePagination, lock, mapper);
    }

    @Override
    public final List<T> findWithLock(final Y filter, final Boolean ignorePagination, final LockModeType lock,
            final PredicateMapper<T, QueryFilter> mapper) {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<T> criteria = builder.createQuery(this.getType());
        final Root<T> root = criteria.from(this.getType());
        criteria.select(root);

        if (mapper != null) {
            mapper.setQuery(criteria);
            criteria.where(mapper.apply(builder, root));
        }

        if (filter != null && filter.getOrder() != null) {
            criteria.orderBy(this.getEntityOrder(builder, root, filter));
        }

        final TypedQuery<T> query = this.getEntityManager().createQuery(criteria);

        if (lock != null) {
            query.setLockMode(lock);
        }

        if (!ignorePagination && filter != null) {
            if (filter.getCount() != 0) {
                query.setMaxResults(filter.getCount());
            }
            query.setFirstResult(filter.getPage() * filter.getCount());
        }

        return query.getResultList();
    }

    @Override
    public final int updateProperty(final Y filter, final String name, final Object value) {
        PredicateMapper<T, QueryFilter> mapper = null;
        if (filter != null) {
            mapper = new PredicateMapper<>(getType(), filter);
        }
        return this.updateProperty(filter, name, value, mapper);
    }

    @Override
    public final int updateProperty(final Y filter, final String name, final Object value,
            final PredicateMapper<T, QueryFilter> mapper) {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaUpdate<T> criteria = builder.createCriteriaUpdate(this.getType());
        final Root<T> root = criteria.from(this.getType());

        criteria.set(name, value);

        if (mapper != null) {
            mapper.setQuery(criteria);
            criteria.where(mapper.apply(builder, root));
        }

        return this.getEntityManager().createQuery(criteria).executeUpdate();
    }

    /**
     * Get JPA order.
     *
     * @param builder
     *            CriteriaBuilder
     * @param root
     *            Root criteria
     * @param entity
     *            Entity to get order information.
     * @return Criteria Order.
     * @throws GlobalException
     *             Throw exception on bad order field.
     */
    protected Order getEntityOrder(final CriteriaBuilder builder, final Root<T> root, final Y entity) {
        try {
            final Expression<String> fieldToOrder = this.getOrderExpression(builder, root, entity.getOrder());

            if ("desc".equalsIgnoreCase(entity.getSort())) {
                return builder.desc(fieldToOrder);
            }
            if ("asc".equalsIgnoreCase(entity.getSort())) {
                return builder.asc(fieldToOrder);
            }

            throw new GlobalException(ErrorType.WRONG_SORT, "sort", entity.getSort());
        } catch (final IllegalArgumentException e) {
            throw new GlobalException(ErrorType.WRONG_ORDER, "order", entity.getOrder());
        }
    }

    /**
     * Get expression from order.
     *
     * @param builder
     *            CriteriaBuilder
     * @param root
     *            Root criteria
     * @param order
     *            Field to order
     * @return Expression from order.
     */
    protected Expression<String> getOrderExpression(final CriteriaBuilder builder, final Root<T> root,
            final String order) {
        return root.get(order);
    }
}
