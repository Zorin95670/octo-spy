package com.octo.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Deployment report view entity.
 *
 * @author Vincent Moittié
 *
 */
@Entity
@Table(name = "deployments_report_view")
public class DeploymentReportView {

    /**
     * Primary key.
     */
    @Id
    @Column(name = "dpl_id", insertable = false, updatable = false, nullable = false)
    private Long id;
    /**
     * Deployment's master project.
     */
    @Column(name = "master_project", insertable = false, updatable = false)
    private Long masterProject;
    /**
     * Deployment's project.
     */
    @Column(name = "project", insertable = false, updatable = false)
    private Long project;
    /**
     * Deployment's environment.
     */
    @Column(name = "environment", insertable = false, updatable = false)
    private Long environment;
    /**
     * Client.
     */
    @Column(name = "client", insertable = false, updatable = false)
    private String client;
    /**
     * The year of the insert date.
     */
    @Column(name = "year", updatable = false)
    private int year;
    /**
     * The month of the insert date.
     */
    @Column(name = "month", updatable = false)
    private int month;
    /**
     * The day of week of the insert date.
     */
    @Column(name = "day_of_week", updatable = false)
    private int dayOfWeek;
    /**
     * The day of the insert date.
     */
    @Column(name = "day", updatable = false)
    private int day;
    /**
     * The hour of the insert date.
     */
    @Column(name = "hour", updatable = false)
    private int hour;

    /**
     * Get deployment id.
     *
     * @return Deployment id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set deployment id.
     *
     * @param id
     *            Deployment id.
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Get master project id.
     *
     * @return Master project id.
     */
    public Long getMasterProject() {
        return masterProject;
    }

    /**
     * Set master project id.
     *
     * @param masterProject
     *            Master project id.
     */
    public void setMasterProject(final Long masterProject) {
        this.masterProject = masterProject;
    }

    /**
     * Get project id.
     *
     * @return Project id.
     */
    public Long getProject() {
        return project;
    }

    /**
     * Set project id.
     *
     * @param project
     *            Project id.
     */
    public void setProject(final Long project) {
        this.project = project;
    }

    /**
     * Get environment id.
     *
     * @return Environment id.
     */
    public Long getEnvironment() {
        return environment;
    }

    /**
     * Set environment id.
     *
     * @param environment
     *            Environment id.
     */
    public void setEnvironment(final Long environment) {
        this.environment = environment;
    }

    /**
     * Get version name.
     *
     * @return Client name.
     */
    public String getClient() {
        return client;
    }

    /**
     * Set version name.
     *
     * @param client
     *            Client name.
     */
    public void setClient(final String client) {
        this.client = client;
    }

    /**
     * Get the year of the insert date..
     *
     * @return Year.
     */
    public int getYear() {
        return year;
    }

    /**
     * Set the year of the insert date..
     *
     * @param year
     *            Year.
     */
    public void setYear(final int year) {
        this.year = year;
    }

    /**
     * Get the month of the insert date..
     *
     * @return Month.
     */
    public int getMonth() {
        return month;
    }

    /**
     * Set the month of the insert date..
     *
     * @param month
     *            Month.
     */
    public void setMonth(final int month) {
        this.month = month;
    }

    /**
     * Get the day of the insert date..
     *
     * @return Day.
     */
    public int getDay() {
        return day;
    }

    /**
     * Set the day of the insert date.
     *
     * @param day
     *            Day.
     */
    public void setDay(final int day) {
        this.day = day;
    }

    /**
     * Get the day of week of the insert date.
     *
     * @return Day of week.
     */
    public int getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Set the day of week of the insert date..
     *
     * @param dayOfWeek
     *            Day of week.
     */
    public void setDayOfWeek(final int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Get the hour of the insert date..
     *
     * @return Hour.
     */
    public int getHour() {
        return hour;
    }

    /**
     * Set the hour of the insert date..
     *
     * @param hour
     *            Hour.
     */
    public void setHour(final int hour) {
        this.hour = hour;
    }
}
