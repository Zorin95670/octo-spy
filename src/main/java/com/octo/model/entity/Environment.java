package com.octo.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.octo.utils.Constants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Environment model.
 *
 * @author Vincent Moittié
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "environments")
public @Data class Environment {
    /**
     * Primary key.
     */
    @Id
    @SequenceGenerator(name = "environments_seq", sequenceName = "environments_env_id_seq", allocationSize = 1,
            initialValue = Constants.START_VALUE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "environments_seq")
    @Column(name = "env_id", updatable = false, nullable = false)
    private Long id;

    /**
     * Environment's name.
     */
    @Column(name = "name", nullable = false, length = Constants.DEFAULT_SIZE_OF_STRING)
    private String name;

    /**
     * Environment's position.
     */
    @Column(name = "position", nullable = false)
    private int position;
}
