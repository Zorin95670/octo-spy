package com.octo.model.dto.project;

import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.octo.utils.predicate.filter.FilterType;
import com.octo.utils.predicate.filter.FilterType.Type;
import com.octo.utils.predicate.filter.QueryFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO to search project.
 *
 * @author Vincent Moittié
 *
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public @Data class SearchProjectViewDTO extends QueryFilter {
    /**
     * Primary key.
     */
    @QueryParam("id")
    @FilterType(type = Type.NUMBER)
    private String id;
    /**
     * Project's name.
     */
    @QueryParam("name")
    @FilterType(type = Type.TEXT)
    private String name;
    /**
     * Project's name.
     */
    @QueryParam("isMaster")
    @FilterType(type = Type.BOOLEAN)
    private String isMaster;
    /**
     * Master project's name.
     */
    @QueryParam("masterProject")
    @FilterType(type = Type.TEXT)
    private String masterProject;
}
