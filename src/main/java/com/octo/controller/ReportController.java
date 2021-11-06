package com.octo.controller;

import javax.annotation.security.PermitAll;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.octo.model.dto.count.MultipleCountDTO;
import com.octo.model.dto.deployment.SearchDeploymentReportViewDTO;
import com.octo.model.entity.DeploymentReportView;
import com.octo.service.MultipleCountService;
import com.octo.utils.bean.BeanMapper;

/**
 * Controller to manage report.
 *
 * @author Vincent Moittié
 *
 */
@Path("/reports")
@Produces(MediaType.APPLICATION_JSON)
@Controller
public class ReportController {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

    /**
     * Service to manage count.
     */
    @Autowired
    private MultipleCountService countService;

    /**
     * Count field of deployments report for restricted value.
     *
     * @param countBody
     *            CountDTO
     * @param deploymentReportDTO
     *            Deployment's report filter.
     * @return Resource to contains deployments and total of this.
     */
    @GET
    @PermitAll
    @Path("/deployments")
    public Response count(@BeanParam final MultipleCountDTO countBody,
            @BeanParam final SearchDeploymentReportViewDTO deploymentReportDTO) {
        LOGGER.info("Received GET request to count report deployments with count DTO {} and search DTO {}.", countBody,
                deploymentReportDTO);
        MultipleCountDTO countDTO = new BeanMapper<>(MultipleCountDTO.class).apply(countBody);
        SearchDeploymentReportViewDTO dto = new BeanMapper<>(SearchDeploymentReportViewDTO.class)
                .apply(deploymentReportDTO);
        return Response.ok(this.countService.count(DeploymentReportView.class, countDTO, dto)).build();
    }
}
