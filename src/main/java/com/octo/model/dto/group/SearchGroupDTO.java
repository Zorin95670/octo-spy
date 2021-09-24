package com.octo.model.dto.group;

import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.octo.utils.predicate.filter.FilterType;
import com.octo.utils.predicate.filter.QueryFilter;
import com.octo.utils.predicate.filter.FilterType.Type;

/**
 * DTO to search group.
 *
 * @author Vincent Moittié
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchGroupDTO extends QueryFilter {
    /**
     * Primary key.
     */
    @QueryParam("id")
    @FilterType(type = Type.NUMBER)
    private String id;
    /**
     * Master project's name.
     */
    @QueryParam("masterProject")
    @FilterType(type = Type.NUMBER)
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
