package com.octo.model.dto.count;

import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.octo.utils.predicate.filter.QueryFilter;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Count DTO.
 *
 * @author Vincent Moittié
 *
 */
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public @Data class CountDTO extends QueryFilter {
    /**
     * Field of model to count.
     */
    @QueryParam("field")
    private String field;
    /**
     * Selected value restrict count.
     */
    @QueryParam("value")
    private String value;
}
