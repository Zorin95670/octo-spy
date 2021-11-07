package com.octo.model.dto.deployment;

import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.octo.utils.predicate.filter.FilterType;
import com.octo.utils.predicate.filter.QueryFilter;
import com.octo.utils.predicate.filter.FilterType.Type;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO to search deployment report view.
 *
 * @author Vincent Moittié
 *
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public @Data class SearchDeploymentReportViewDTO extends QueryFilter {
    /**
     * Primary key.
     */
    @FilterType(type = Type.NUMBER)
    @QueryParam("id")
    private String id;
    /**
     * Deployment's master project id.
     */
    @FilterType(type = Type.NUMBER)
    @QueryParam("masterProject")
    private String masterProject;
    /**
     * Deployment's project id.
     */
    @FilterType(type = Type.NUMBER)
    @QueryParam("project")
    private String project;
    /**
     * Deployment's environment id.
     */
    @FilterType(type = Type.NUMBER)
    @QueryParam("environment")
    private String environment;
    /**
     * Client.
     */
    @FilterType(type = Type.TEXT)
    @QueryParam("client")
    private String client;
    /**
     * The year of the insert date.
     */
    @FilterType(type = Type.NUMBER)
    @QueryParam("year")
    private String year;
    /**
     * The month of the insert date.
     */
    @FilterType(type = Type.NUMBER)
    @QueryParam("month")
    private String month;
    /**
     * The day of the insert date.
     */
    @FilterType(type = Type.NUMBER)
    @QueryParam("day")
    private String day;
    /**
     * The day of week of the insert date.
     */
    @FilterType(type = Type.NUMBER)
    @QueryParam("dayOfWeek")
    private String dayOfWeek;
    /**
     * The hour of the insert date.
     */
    @FilterType(type = Type.NUMBER)
    @QueryParam("hour")
    private String hour;
}
