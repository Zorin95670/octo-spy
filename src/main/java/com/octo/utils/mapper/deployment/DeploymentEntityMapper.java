package com.octo.utils.mapper.deployment;

import java.util.function.Function;

import com.octo.model.dto.deployment.NewDeploymentDTO;
import com.octo.model.entity.Deployment;

/**
 * Map deployment DTO to entity.
 *
 * @author vmoittie
 *
 */
public class DeploymentEntityMapper implements Function<NewDeploymentDTO, Deployment> {

    @Override
    public final Deployment apply(final NewDeploymentDTO dto) {
        if (dto == null) {
            return null;
        }

        Deployment entity = new Deployment();
        entity.setAlive(dto.isAlive());
        entity.setClient(dto.getClient());
        entity.setVersion(dto.getVersion());

        return entity;
    }

}
