package com.octo.dao.filter.deployment;

import java.util.function.BiFunction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.octo.model.entity.Deployment;

/**
 * Filter to get previous active deployment.
 *
 * @author vmoittie
 *
 */
public class PreviousDeploymentFilter implements BiFunction<CriteriaBuilder, Root<Deployment>, Predicate> {

    /**
     * Deployment to filter.
     */
    private Deployment entity;

    /**
     * Initialize deployment to filter.
     *
     * @param entity
     *            Deployment to filter.
     */
    public PreviousDeploymentFilter(final Deployment entity) {
        this.entity = entity;
    }

    @Override
    public final Predicate apply(final CriteriaBuilder builder, final Root<Deployment> root) {
        return builder.and(builder.equal(root.get("project"), entity.getProject().getId()),
                builder.equal(root.get("environment"), entity.getEnvironment().getId()),
                builder.equal(root.get("client"), entity.getClient()), builder.equal(root.get("alive"), true));
    }

}
