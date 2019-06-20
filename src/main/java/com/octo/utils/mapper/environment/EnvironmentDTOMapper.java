package com.octo.utils.mapper.environment;

import java.util.function.Function;

import com.octo.model.dto.environment.EnvironmentDTO;
import com.octo.model.entity.Environment;

/**
 * Map environment entity to DTO.
 *
 * @author vmoittie
 *
 */
public class EnvironmentDTOMapper implements Function<Environment, EnvironmentDTO> {

    @Override
    public final EnvironmentDTO apply(final Environment entity) {
        if (entity == null) {
            return null;
        }

        return new EnvironmentDTO(entity.getId(), entity.getName());
    }

}
