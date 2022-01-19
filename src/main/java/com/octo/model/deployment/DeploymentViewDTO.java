package com.octo.model.deployment;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Default deployment DTO.
 *
 * @author Vincent Moittié
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DeploymentViewDTO extends DeploymentDTO {
    /**
     * Is deployment is in progress.
     */
    private boolean inProgress;
}
