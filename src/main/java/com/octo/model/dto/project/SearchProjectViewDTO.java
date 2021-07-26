package com.octo.model.dto.project;

import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.octo.utils.predicate.filter.FilterType;
import com.octo.utils.predicate.filter.FilterType.Type;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * DTO to search project.
 *
 * @author Vincent Moittié
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchProjectViewDTO extends QueryFilter {
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
     * Master project's name.
     */
    @QueryParam("masterProject")
    @FilterType(type = Type.TEXT)
    private String masterProject;

    /**
     * Get primary key value.
     *
     * @return Primary key value.
     */
    public String getId() {
        return id;
    }

    /**
     * Set primary key value.
     *
     * @param id
     *            Primary key value.
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Get project's name.
     *
     * @return Project's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set project's name.
     *
     * @param name
     *            Project's name.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Get master project's name.
     *
     * @return Master project's name.
     */
    public String getMasterProject() {
        return masterProject;
    }

    /**
     * Set master project's name.
     *
     * @param masterProject
     *            Master project's name.
     */
    public void setMasterProject(final String masterProject) {
        this.masterProject = masterProject;
    }

}
