package com.octo.persistence.repository;

import com.octo.persistence.model.Deployment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DAO for deployment entity.
 *
 * @author Vincent Moittié
 */
@Repository
public interface DeploymentRepository extends CrudRepository<Deployment, Long>, JpaSpecificationExecutor<Deployment> {

    /**
     * Get all distinct clients.
     *
     * @param pageable Page options.
     * @return All paginated distinct clients.
     */
    @Query(value = "select distinct client from deployments", nativeQuery = true)
    Page<String> findAllDistinctClient(Pageable pageable);

    /**
     * Get on deployment by filter.
     *
     * @param specification Filter options.
     * @return Optional of deployment.
     */
    Optional<Deployment> findOne(Specification<Deployment> specification);
}
