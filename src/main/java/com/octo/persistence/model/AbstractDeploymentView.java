package com.octo.persistence.model;

import com.octo.persistence.specification.filter.FilterType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Default deployment view entity.
 *
 * @author Vincent Moittié
 */
@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractDeploymentView extends AbstractEntity {

    /**
     * Primary key.
     */
    @Id
    @Column(name = "dpl_id", insertable = false, updatable = false, nullable = false)
    @FilterType(type = FilterType.Type.NUMBER)
    private Long id;
    /**
     * Index of project.
     */
    @Column(name = "pro_id", insertable = false, updatable = false, nullable = false)
    @FilterType(type = FilterType.Type.NUMBER)
    private Long projectId;
    /**
     * Deployment's environment.
     */
    @Column(name = "environment", insertable = false, updatable = false)
    @FilterType(type = FilterType.Type.TEXT)
    private String environment;
    /**
     * Deployment's project.
     */
    @Column(name = "project", insertable = false, updatable = false)
    @FilterType(type = FilterType.Type.TEXT)
    private String project;
    /**
     * Deployment's project color.
     */
    @Column(name = "color", insertable = false, updatable = false)
    @FilterType(type = FilterType.Type.TEXT)
    private String color;
    /**
     * Deployment's master project.
     */
    @Column(name = "master_project", insertable = false, updatable = false)
    @FilterType(type = FilterType.Type.TEXT)
    private String masterProject;
    /**
     * Deployment's master project color.
     */
    @Column(name = "master_project_color", insertable = false, updatable = false)
    @FilterType(type = FilterType.Type.TEXT)
    private String masterProjectColor;
    /**
     * Deployed version.
     */
    @Column(name = "version", insertable = false, updatable = false)
    @FilterType(type = FilterType.Type.TEXT)
    private String version;
    /**
     * Client.
     */
    @Column(name = "client", insertable = false, updatable = false)
    @FilterType(type = FilterType.Type.TEXT)
    private String client;
    /**
     * Is in progress.
     */
    @Column(name = "in_progress", insertable = false, updatable = false)
    @FilterType(type = FilterType.Type.BOOLEAN)
    private boolean inProgress;
}
