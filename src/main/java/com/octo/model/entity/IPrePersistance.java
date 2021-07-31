package com.octo.model.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.PrePersist;

/**
 * Interface to add pre-persistance on entity.
 *
 * @author Vincent Moittié
 *
 */
public interface IPrePersistance {

    /**
     * Set insertDate before persist in repository.
     */
    @PrePersist
    default void prePersist() {
        this.setInsertDate(Timestamp.valueOf(LocalDateTime.now()));
    }

    /**
     * Set the creation date of this entity.
     *
     * @param insertDate
     *            Creation date.
     */
    void setInsertDate(Timestamp insertDate);

}
