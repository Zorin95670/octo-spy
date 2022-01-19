package com.octo.persistence.repository;

import com.octo.persistence.model.InformationView;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO for information entity.
 *
 * @author Vincent Moittié
 */
@Repository
public interface InformationRepository extends CrudRepository<InformationView, Long>,
        JpaSpecificationExecutor<InformationView> {

}
