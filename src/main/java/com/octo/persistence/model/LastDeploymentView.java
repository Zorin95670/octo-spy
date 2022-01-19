package com.octo.persistence.model;

import com.octo.persistence.specification.filter.FilterType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity of last deployment.
 *
 * @author Vincent Moittié
 */
@Entity
@Table(name = "last_deployments_view")
@EqualsAndHashCode(callSuper = true)
@Data
public class LastDeploymentView extends AbstractDeploymentView {

    /**
     * Is deployment on master project.
     */
    @Column(name = "on_master_project")
    @FilterType(type = FilterType.Type.BOOLEAN)
    private boolean onMasterProject;
}
