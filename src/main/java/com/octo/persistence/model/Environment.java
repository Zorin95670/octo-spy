package com.octo.persistence.model;


import com.octo.model.common.Constants;
import com.octo.persistence.specification.filter.FilterType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Environment model.
 *
 * @author Vincent Moittié
 */
@Entity
@Table(name = "environments")
@Data
public class Environment {

    /**
     * Primary key.
     */
    @Id
    @SequenceGenerator(name = "environments_seq", sequenceName = "environments_env_id_seq", allocationSize = 1,
            initialValue = Constants.START_VALUE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "environments_seq")
    @Column(name = "env_id", updatable = false, nullable = false)
    @FilterType(type = FilterType.Type.NUMBER)
    private Long id;

    /**
     * Environment's name.
     */
    @Column(name = "name", nullable = false, length = Constants.DEFAULT_SIZE_OF_STRING)
    @FilterType(type = FilterType.Type.TEXT)
    private String name;

    /**
     * Environment's position.
     */
    @Column(name = "position", nullable = false)
    @FilterType(type = FilterType.Type.NUMBER)
    private int position;
}
