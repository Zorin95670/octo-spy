package com.octo.service;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.Map;

/**
 * Deployment service.
 */
public interface DeploymentReportViewService extends ServiceHelper {

    /**
     * Count deployment report.
     *
     * @param filters Filter options.
     * @param fields Field names.
     * @return Count object.
     */
    JsonNode count(Map<String, String> filters, List<String> fields);
}
