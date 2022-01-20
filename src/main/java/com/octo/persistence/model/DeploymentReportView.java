package com.octo.persistence.model;

import com.octo.persistence.specification.filter.FilterType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Deployment report view entity.
 *
 * @author Vincent Moittié
 */
@Entity
@Table(name = "deployments_report_view")
@Data
public class DeploymentReportView {

    /**
     * Primary key.
     */
    @Id
    @Column(name = "dpl_id", insertable = false, updatable = false, nullable = false)
    @FilterType(type = FilterType.Type.NUMBER)
    private Long id;
    /**
     * Deployment's master project.
     */
    @Column(name = "master_project", insertable = false, updatable = false)
    @FilterType(type = FilterType.Type.NUMBER)
    private Long masterProject;
    /**
     * Deployment's project.
     */
    @Column(name = "project", insertable = false, updatable = false)
    @FilterType(type = FilterType.Type.NUMBER)
    private Long project;
    /**
     * Deployment's environment.
     */
    @Column(name = "environment", insertable = false, updatable = false)
    @FilterType(type = FilterType.Type.NUMBER)
    private Long environment;
    /**
     * Client.
     */
    @Column(name = "client", insertable = false, updatable = false)
    @FilterType(type = FilterType.Type.TEXT)
    private String client;
    /**
     * The year of the insert date.
     */
    @Column(name = "year", updatable = false)
    @FilterType(type = FilterType.Type.NUMBER)
    private int year;
    /**
     * The month of the insert date.
     */
    @Column(name = "month", updatable = false)
    @FilterType(type = FilterType.Type.NUMBER)
    private int month;
    /**
     * The day of week of the insert date.
     */
    @Column(name = "day_of_week", updatable = false)
    @FilterType(type = FilterType.Type.NUMBER)
    private int dayOfWeek;
    /**
     * The day of the insert date.
     */
    @Column(name = "day", updatable = false)
    @FilterType(type = FilterType.Type.NUMBER)
    private int day;
    /**
     * The hour of the insert date.
     */
    @Column(name = "hour", updatable = false)
    @FilterType(type = FilterType.Type.NUMBER)
    private int hour;
}
