package com.octo.model.filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Class to extract filter from query.
 *
 * @param <T>
 *            Type of expression.
 */
public interface IQueryFilter<T> {

    /**
     * Extract Filter from query value.
     *
     * @return True if there is a predicate, otherwise false.
     */
    boolean extract();

    /**
     * Create predicate from entity.
     *
     * @param <Y>
     *            Entity class.
     * @param builder
     *            Criteria builder.
     * @param root
     *            Entity root.
     * @return Predicate from entity.
     */
    <Y> Predicate getPredicate(CriteriaBuilder builder, Root<Y> root);

    /**
     * Create predicate from entity.
     *
     * @param builder
     *            Criteria builder.
     * @param field
     *            Entity expression.
     * @return Predicate from entity.
     */
    Predicate getPredicate(CriteriaBuilder builder, Expression<T> field);

}
