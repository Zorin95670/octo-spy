package com.octo.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity of last deployment.
 *
 * @author Vincent Moittié
 *
 */
@Entity
@Table(name = "last_deployments_view")
public class LastDeploymentView extends AbstractDeploymentView {

}
