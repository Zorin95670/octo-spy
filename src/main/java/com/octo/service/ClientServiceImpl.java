package com.octo.service;

import com.octo.persistence.repository.DeploymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Implementation of client service.
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    /**
     * Deployment repository.
     */
    @Autowired
    private DeploymentRepository deploymentRepository;

    @Override
    public Page<String> findAll(final Pageable pageable) {
        return this.deploymentRepository.findAllDistinctClient(PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "client"))
        ));
    }
}
