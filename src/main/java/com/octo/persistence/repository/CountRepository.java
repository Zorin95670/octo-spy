package com.octo.persistence.repository;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Count DAO, to count object in database.
 *
 * @param <T> Entity class.
 * @author Vincent Moittié
 */
@Repository
public interface CountRepository<T> {

    /**
     * Count entity field.
     *
     * @param entity Entity class.
     * @param specification Filter options
     * @param field Field to count.
     * @param value Default value for count.
     * @return Count object.
     */
    JsonNode count(Class<T> entity, Specification<T> specification, String field, String value);

    /**
     * Count entity fields.
     *
     * @param entity Entity class.
     * @param specification Filter options
     * @param fields Fields to count.
     * @return Count object.
     */
    JsonNode count(Class<T> entity, Specification<T> specification, List<String> fields);
}
