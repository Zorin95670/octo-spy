package com.octo.model.dto.deployment;

import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.octo.utils.predicate.filter.FilterType;
import com.octo.utils.predicate.filter.QueryFilter;
import com.octo.utils.predicate.filter.FilterType.Type;

/**
 * DTO to search deployment report view.
 *
 * @author Vincent Moittié
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchDeploymentReportViewDTO extends QueryFilter {
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

    /**
     * Get id.
     *
     * @return Id.
     */
    public String getId() {
        return id;
    }

    /**
     * Set id.
     *
     * @param id
     *            Id.
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Get master project id.
     *
     * @return Master project id.
     */
    public String getMasterProject() {
        return masterProject;
    }

    /**
     * Set master project id.
     *
     * @param masterProject
     *            Master project id.
     */
    public void setMasterProject(final String masterProject) {
        this.masterProject = masterProject;
    }

    /**
     * Get project's id.
     *
     * @return Project's id.
     */
    public String getProject() {
        return project;
    }

    /**
     * Set project's id.
     *
     * @param project
     *            Project's id.
     */
    public void setProject(final String project) {
        this.project = project;
    }

    /**
     * Get environment's id.
     *
     * @return Environment's id.
     */
    public String getEnvironment() {
        return environment;
    }

    /**
     * Set environment's id.
     *
     * @param environment
     *            Environment's id.
     */
    public void setEnvironment(final String environment) {
        this.environment = environment;
    }

    /**
     * Get client.
     *
     * @return Client.
     */
    public String getClient() {
        return client;
    }

    /**
     * Set client.
     *
     * @param client
     *            Client.
     */
    public void setClient(final String client) {
        this.client = client;
    }

    /**
     * Get the year of the insert date..
     *
     * @return Year.
     */
    public String getYear() {
        return year;
    }

    /**
     * Set the year of the insert date..
     *
     * @param year
     *            Year.
     */
    public void setYear(final String year) {
        this.year = year;
    }

    /**
     * Get the month of the insert date..
     *
     * @return Month.
     */
    public String getMonth() {
        return month;
    }

    /**
     * Set the month of the insert date..
     *
     * @param month
     *            Month.
     */
    public void setMonth(final String month) {
        this.month = month;
    }

    /**
     * Get the day of the insert date..
     *
     * @return Day.
     */
    public String getDay() {
        return day;
    }

    /**
     * Set the day of the insert date.
     *
     * @param day
     *            Day.
     */
    public void setDay(final String day) {
        this.day = day;
    }

    /**
     * Get the day of week of the insert date.
     *
     * @return Day of week.
     */
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Set the day of week of the insert date..
     *
     * @param dayOfWeek
     *            Day of week.
     */
    public void setDayOfWeek(final String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Get the hour of the insert date..
     *
     * @return Hour.
     */
    public String getHour() {
        return hour;
    }

    /**
     * Set the hour of the insert date..
     *
     * @param hour
     *            Hour.
     */
    public void setHour(final String hour) {
        this.hour = hour;
    }
}
