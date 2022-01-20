package com.octo.persistence.repository;

import com.octo.persistence.model.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DAO for user entity.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {

    /**
     * Get user by login.
     *
     * @param login Login to find.
     * @return Optional of user.
     */
    Optional<User> findByLogin(String login);
}
