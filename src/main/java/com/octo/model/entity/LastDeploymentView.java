package com.octo.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity of last deployment.
 *
 * @author Vincent Moittié
 *
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "last_deployments_view")
public @Data class LastDeploymentView extends AbstractDeploymentView {

    /**
     * Is deployment on master project.
     */
    @Column(name = "on_master_project")
    private boolean onMasterProject;
}
