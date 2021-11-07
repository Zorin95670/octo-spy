package com.octo.model.dto.count;

import java.util.List;

import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.octo.utils.predicate.filter.QueryFilter;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Multiple count DTO.
 *
 * @author Vincent Moittié
 *
 */
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public @Data class MultipleCountDTO extends QueryFilter {
    /**
     * Fields of model to count.
     */
    @QueryParam("fields")
    private List<String> fields;
}
