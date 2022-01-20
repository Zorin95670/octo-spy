package com.octo.service;

import com.octo.persistence.model.LastDeploymentView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Deployment service.
 */
public interface LastDeploymentViewService {

    /**
     * find last deployments.
     *
     * @param filters Filter options.
     * @param pageable Page options.
     * @return deployments.
     */
    Page<LastDeploymentView> find(Map<String, String> filters, Pageable pageable);
}
