package com.octo.model.dto.group;

import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.octo.utils.predicate.filter.FilterType;
import com.octo.utils.predicate.filter.QueryFilter;
import com.octo.utils.predicate.filter.FilterType.Type;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO to search group.
 *
 * @author Vincent Moittié
 *
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public @Data class SearchGroupDTO extends QueryFilter {
    /**
     * Primary key.
     */
    @QueryParam("id")
    @FilterType(type = Type.NUMBER)
    private String id;
    /**
     * Master project's name.
     */
    @QueryParam("masterProject")
    @FilterType(type = Type.NUMBER)
    private String masterProject;
}
