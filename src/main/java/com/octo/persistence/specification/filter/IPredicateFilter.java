package com.octo.persistence.specification.filter;

import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

/**
 * Predicate extractor.
 *
 * @author Vincent Moittié
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
     * @param <T>     Entity class.
     * @param <Y>     Entity class.
     * @param builder Criteria builder.
     * @param root    Entity root.
     * @param query   Default Query.
     * @return Predicate from entity.
     */
    <T, Y> Predicate getPredicate(CriteriaBuilder builder, From<T, Y> root, CommonAbstractCriteria query);

}
