package com.octo.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.octo.utils.Constants;

/**
 * Environment model.
 *
 * @author Vincent Moittié
 *
 */
@Entity
@Table(name = "environments")
public class Environment {

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

    /**
     * Get primary key value.
     *
     * @return Primary key value.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set primary key value.
     *
     * @param id
     *            Primary key value.
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Get environment's name.
     *
     * @return Environment's name.
     */
    public String getName() {
        return name;
    }

    /**
     * eSet environment's name.
     *
     * @param name
     *            Environment's name.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Get environment's position.
     *
     * @return Environment's position.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Set environment's position.
     *
     * @param position
     *            Environment's position.
     */
    public void setPosition(final int position) {
        this.position = position;
    }
}
