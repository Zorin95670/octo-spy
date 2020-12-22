package com.octo.model.dto.deployment;

import com.cji.utils.predicate.filter.FilterType;
import com.cji.utils.predicate.filter.FilterType.Type;
import com.cji.utils.predicate.filter.QueryFilter;

/**
 * DTO to search progress of deployment.
 */
public class SearchProgressDeploymentDTO extends QueryFilter {
    /**
     * Primary key.
     */
    @FilterType(type = Type.NUMBER)
    private String id;
    /**
     * Deployment's deployment id.
     */
    @FilterType(type = Type.NUMBER)
    private String deployment;

    /**
     * Get id.
     *
     * @return Id.
     */
    public String getId() {
        return id;
    }

    /**
     * Set id.
     *
     * @param id
     *            Id.
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Get deployment id.
     *
     * @return Deployment id.
     */
    public String getDeployment() {
        return deployment;
    }

    /**
     * Set deployment id.
     *
     * @param deployment
     *            Deployment id.
     */
    public void setDeployment(final String deployment) {
        this.deployment = deployment;
    }
}
