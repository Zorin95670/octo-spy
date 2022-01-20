package com.octo.persistence.repository;

import com.octo.persistence.model.Group;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DAO for group entity.
 *
 * @author Vincent Moittié
 */
@Repository
public interface GroupRepository extends CrudRepository<Group, Long>, JpaSpecificationExecutor<Group> {

    /**
     * Find group by master project id.
     *
     * @param id Id to find.
     * @return Optional of group.
     */
    Optional<Group> findByMasterProjectId(Long id);
}
