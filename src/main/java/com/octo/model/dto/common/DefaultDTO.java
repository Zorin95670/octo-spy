package com.octo.model.dto.common;

import com.octo.utils.mapper.common.ToJsonMapper;

/**
 * Default DTO.
 *
 * @author vmoittie
 *
 */
public abstract class DefaultDTO {

    @Override
    public final String toString() {
        return new ToJsonMapper<>(false).apply(this);
    }

}
