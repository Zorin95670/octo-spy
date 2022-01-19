package com.octo.persistence.model;

import com.octo.persistence.specification.filter.FilterType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Deployment view entity.
 *
 * @author Vincent Moittié
 */
@Entity
@Table(name = "deployments_view")
@EqualsAndHashCode(callSuper = true)
@Data
public class DeploymentView extends AbstractDeploymentView {
    /**
     * Is deployment is still alive.
     */
    @Column(name = "alive")
    @FilterType(type = FilterType.Type.BOOLEAN)
    private boolean alive;

}
