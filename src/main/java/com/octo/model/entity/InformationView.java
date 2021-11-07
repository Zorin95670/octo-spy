package com.octo.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.octo.utils.Constants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Information entity.
 *
 * @author Vincent Moittié
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "information_view")
public @Data class InformationView {

    /**
     * Primary key.
     */
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    /**
     * Version.
     */
    @Column(name = "version", nullable = false, length = Constants.DEFAULT_SIZE_OF_STRING)
    private String version;
}
