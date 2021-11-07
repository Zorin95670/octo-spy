package com.octo.model.dto.deployment;

import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.octo.utils.predicate.filter.FilterType;
import com.octo.utils.predicate.filter.FilterType.Type;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO to search last deployment view.
 *
 * @author Vincent Moittié
 *
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public @Data class SearchLastDeploymentViewDTO extends SearchDeploymentViewDTO {
    /**
     * Is deployment on master project.
     */
    @FilterType(type = Type.BOOLEAN)
    @QueryParam("onMasterProject")
    private String onMasterProject;
}
