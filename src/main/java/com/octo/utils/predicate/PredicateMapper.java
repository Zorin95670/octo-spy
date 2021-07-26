package com.octo.utils.predicate;

import java.util.List;
import java.util.function.BiFunction;

import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.octo.utils.predicate.filter.IPredicateFilter;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * Convert DTO to array of predicates.
 *
 * @param <T>
 *            Entity class.
 * @param <Y>
 *            DTO class.
 *
 * @author Vincent Moittié
 *
 */
public class PredicateMapper<T, Y extends QueryFilter> implements BiFunction<CriteriaBuilder, Root<T>, Predicate[]> {

    /**
     * DTO.
     */
    private final Y dto;
    /**
     * Entity class.
     */
    private final Class<T> entityClass;
    /**
     * Query.
     */
    private CommonAbstractCriteria query;

    /**
     * Constructor.
     *
     * @param entityClass
     *            Entity class.
     * @param dto
     *            DTO object.
     */
    public PredicateMapper(final Class<T> entityClass, final Y dto) {
        this(entityClass, dto, null);
    }

    /**
     * Constructor.
     *
     * @param entityClass
     *            Entity class.
     * @param dto
     *            DTO object.
     * @param query
     *            Query.
     */
    public PredicateMapper(final Class<T> entityClass, final Y dto, final CommonAbstractCriteria query) {
        this.dto = dto;
        this.entityClass = entityClass;
        this.setQuery(query);
    }

    /**
     * Override this if you need a specific mapping.
     */
    @Override
    public Predicate[] apply(final CriteriaBuilder builder, final Root<T> root) {
        final List<IPredicateFilter> filters = this.dto.getFilters(this.entityClass);
        return filters.stream().map(filter -> filter.getPredicate(builder, root, getQuery()))
                .toArray(size -> new Predicate[size]);
    }

    /**
     * Get default query to apply predicates.
     *
     * @return Query.
     */
    public CommonAbstractCriteria getQuery() {
        return query;
    }

    /**
     * Set default query to apply predicates.
     *
     * @param query
     *            Query.
     */
    public void setQuery(final CommonAbstractCriteria query) {
        this.query = query;
    }
}
