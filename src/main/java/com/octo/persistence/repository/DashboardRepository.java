package com.octo.persistence.repository;

import com.octo.persistence.model.Dashboard;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO for dashboard entity.
 *
 * @author Vincent Moittié
 */
@Repository
public interface DashboardRepository extends CrudRepository<Dashboard, Long>, JpaSpecificationExecutor<Dashboard> {

}
