package com.octo.utils.mapper.deployment;

import java.util.function.Function;

import com.octo.model.dto.deployment.DeploymentDTO;
import com.octo.model.entity.Deployment;

/**
 * Map deployment entity to DTO.
 *
 * @author vmoittie
 *
 */
public class DeploymentDTOMapper implements Function<Deployment, DeploymentDTO> {

    @Override
    public final DeploymentDTO apply(final Deployment entity) {
        if (entity == null) {
            return null;
        }

        DeploymentDTO dto = new DeploymentDTO();

        dto.setId(entity.getId());
        dto.setEnvironment(entity.getEnvironment());
        dto.setProject(entity.getProject());
        dto.setClient(entity.getClient());
        dto.setAlive(entity.isAlive());
        dto.setInsertDate(entity.getInsertDate());
        dto.setUpdateDate(entity.getUpdateDate());
        dto.setVersion(entity.getVersion());

        return dto;
    }

}
