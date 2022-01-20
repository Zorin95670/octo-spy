package com.octo.service;

import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Helper for service.
 */
@Transactional
public interface ServiceHelper {

    /**
     * Load entity by id, throw exception in case of unknown entity.
     *
     * @param repository Repository.
     * @param id         Entity id.
     * @param <T>        Entity class.
     * @param <Y>        Id class.
     * @return Entity.
     */
    default <T, Y> T loadEntityById(CrudRepository<T, Y> repository, final Y id) {
        if (id == null) {
            throw new GlobalException(ErrorType.EMPTY_VALUE, "id");
        }

        return repository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorType.ENTITY_NOT_FOUND, "id", String.valueOf(id)));
    }
}
