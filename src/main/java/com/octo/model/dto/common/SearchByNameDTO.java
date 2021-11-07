package com.octo.model.dto.common;

import com.octo.utils.predicate.filter.FilterType;
import com.octo.utils.predicate.filter.FilterType.Type;

import lombok.*;

import com.octo.utils.predicate.filter.QueryFilter;

/**
 * DTO to search entity by name.
 *
 * @author Vincent Moittié
 *
 */
@AllArgsConstructor
@NoArgsConstructor
public @Data class SearchByNameDTO extends QueryFilter {
    /**
     * Entity's name.
     */
    @FilterType(type = Type.TEXT)
    private String name;
}
