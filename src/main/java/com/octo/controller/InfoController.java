package com.octo.controller;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.octo.model.dto.common.ProjectInformationRecord;
import com.octo.utils.Configuration;

/**
 * Controller to manage project version.
 *
 * @author Vincent Moittié
 *
 */
@Path("/info")
@Produces(MediaType.APPLICATION_JSON)
@Controller
public class InfoController {

    /**
     * Configuration class.
     */
    @Autowired
    private Configuration configuration;

    /**
     * Endpoint to return version of project.
     *
     * @return Project version.
     */
    @GET
    @PermitAll
    public final ProjectInformationRecord getVersion() {
        return new ProjectInformationRecord(this.configuration.getProject(), this.configuration.getVersion(),
                this.configuration.getEnvironment(), this.configuration.getClient());
    }
}
