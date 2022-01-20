package com.octo.service;

import com.octo.persistence.model.LastDeploymentView;
import com.octo.persistence.repository.LastDeploymentViewRepository;
import com.octo.persistence.specification.SpecificationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

/**
 * Implementation of deployment service.
 *
 * @author Vincent Moittié
 */
@Service
@Transactional
public class LastDeploymentViewServiceImpl implements LastDeploymentViewService {

    /**
     * Deployment's Repository.
     */
    @Autowired
    private LastDeploymentViewRepository lastDeploymentViewRepository;

    @Override
    public Page<LastDeploymentView> find(final Map<String, String> filters, final Pageable pageable) {
        return this.lastDeploymentViewRepository.findAll(new SpecificationHelper<>(LastDeploymentView.class, filters),
                pageable);
    }
}
