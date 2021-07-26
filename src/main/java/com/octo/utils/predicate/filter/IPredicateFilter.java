package com.octo.utils.predicate.filter;

import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import com.octo.utils.predicate.PredicateOperator;

/**
 * Predicate extractor.
 *
 * @author Vincent Moittié
 *
 */
public interface IPredicateFilter {

    /**
     * Extract Filter from query value.
     *
     * @return True if there is a predicate, otherwise false.
     */
    boolean extract();

    /**
     * Create predicate from entity.
     *
     * @param <T>
     *            Entity class.
     * @param <Y>
     *            Entity class.
     * @param builder
     *            Criteria builder.
     * @param root
     *            Entity root.
     * @param query
     *            Default Query.
     * @return Predicate from entity.
     */
    <T, Y> Predicate getPredicate(CriteriaBuilder builder, From<T, Y> root, CommonAbstractCriteria query);

    /**
     * Create predicate from entity.
     *
     * @param <T>
     *            Entity class.
     * @param <Y>
     *            Entity class.
     * @param index
     *            Value index.
     * @param builder
     *            Criteria builder.
     * @param root
     *            Entity root.
     * @return Predicate from entity.
     */
    <T, Y> Predicate getPredicate(int index, CriteriaBuilder builder, From<T, Y> root);

    /**
     * Create predicate from entity.
     *
     * @param index
     *            Value index.
     * @param builder
     *            Criteria builder.
     * @param field
     *            Entity expression.
     * @param <T>
     *            Entity class.
     * @return Predicate from entity.
     */
    <T> Predicate getPredicate(int index, CriteriaBuilder builder, Expression<T> field);

    /**
     * Indicate if this filter for this value is specific.
     *
     * @return Boolean.
     */
    boolean isSpecificOperator();

    /**
     * Get specific predicate from text.
     *
     * @param index
     *            Value index.
     * @return Predicate.
     */
    PredicateOperator getSpecificOperator(int index);

    /**
     * Get default operator.
     *
     * @return Equals operator.
     */
    PredicateOperator getDefaultOperator();

    /**
     * Return null or not null predicate.
     *
     * @param index
     *            Value index.
     * @param builder
     *            Criteria builder.
     * @param field
     *            Entity expression.
     * @param <T>
     *            Field type.
     * @return Null predicate or predicate representing null clause.
     */
    <T> Predicate getNullPredicate(int index, CriteriaBuilder builder, Expression<T> field);
}
