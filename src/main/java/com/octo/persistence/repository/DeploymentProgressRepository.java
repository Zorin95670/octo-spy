package com.octo.persistence.repository;

import com.octo.persistence.model.DeploymentProgress;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DAO for progress of deployment entity.
 *
 * @author Vincent Moittié
 */
@Repository
public interface DeploymentProgressRepository extends CrudRepository<DeploymentProgress, Long>,
        JpaSpecificationExecutor<DeploymentProgress> {

    /**
     * Find deployment progress by deployment id.
     *
     * @param id Id to find.
     * @return Optional of DeploymentProgress.
     */
    Optional<DeploymentProgress> findByDeploymentId(Long id);

    /**
     * Execute stored procedure to delete all progress.
     *
     * @param project Project filter.
     * @param environment Environment filter.
     * @param client Client filter.
     */
    @Modifying
    @Query(value = "CALL delete_all_progress(?1, ?2, ?3)", nativeQuery = true)
    void deleteAllProgress(String project, String environment, String client);
}
