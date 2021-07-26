package com.octo.model.dto.deployment;

import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.octo.utils.predicate.filter.FilterType;
import com.octo.utils.predicate.filter.FilterType.Type;

/**
 * DTO to search last deployment view.
 *
 * @author Vincent Moittié
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchLastDeploymentViewDTO extends SearchDeploymentViewDTO {
    
    /**
     * Is deployment on master project.
     */
    @FilterType(type = Type.BOOLEAN)
    @QueryParam("onMasterProject")
    private String onMasterProject;

    /**
     * Deployment on master project.
     *
     * @return On master project.
     */
    public String getOnMasterProject() {
        return onMasterProject;
    }

    /**
     * Set deployment on master project.
     *
     * @param onMasterProject
     *            On master project.
     */
    public void setAlive(final String onMasterProject) {
        this.onMasterProject = onMasterProject;
    }

}
