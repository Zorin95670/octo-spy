package com.octo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Client service.
 *
 * @author Vincent Moittié
 */
public interface ClientService {
    /**
     * Get all distinct client's names.
     *
     * @param pageable Page options.
     * @return List of distinct client's names.
     */
    Page<String> findAll(Pageable pageable);
}
