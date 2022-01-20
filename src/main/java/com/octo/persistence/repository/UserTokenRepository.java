package com.octo.persistence.repository;

import com.octo.persistence.model.UserToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DAO for user token entity.
 */
@Repository
public interface UserTokenRepository extends CrudRepository<UserToken, Long>, JpaSpecificationExecutor<UserToken> {

    /**
     * Find user token by user id.
     *
     * @param id Id to find.
     * @param pageable Page options.
     * @return List of user tokens.
     */
    Page<UserToken> findByUserId(Long id, Pageable pageable);

    /**
     * Get user token by user id and token name.
     *
     * @param id Id to find.
     * @param name Name to find.
     * @return Optional of token.
     */
    Optional<UserToken> findByUserIdAndName(Long id, String name);
}
