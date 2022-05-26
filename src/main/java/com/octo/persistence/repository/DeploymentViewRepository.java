package com.octo.persistence.repository;

import com.octo.persistence.model.DeploymentView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO for deployment entity.
 *
 * @author Vincent Moittié
 */
@Repository
public interface DeploymentViewRepository extends CrudRepository<DeploymentView, Long>,
        JpaSpecificationExecutor<DeploymentView>, CountRepository<DeploymentView> {

    /**
     * Get deployment view by id.
     *
     * @param id Id to find.
     * @return Deployment view.
     */
    DeploymentView getById(Long id);

    /**
     * Get all deployment views.
     *
     * @param specification Filter options.
     * @param pageable      Page options.
     * @return List of deployment views.
     */
    Page<DeploymentView> findAll(Specification<DeploymentView> specification, Pageable pageable);
}
