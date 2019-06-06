package com.octo.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.octo.utils.Constants;

/**
 * Environment model.
 *
 * @author vmoittie
 *
 */
@Entity
@Table(name = "environments")
public class Environment {

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "env_id", updatable = false, nullable = false)
    private Long id;

    /**
     * Environment's name.
     */
    @Column(name = "name", nullable = false, length = Constants.DEFAULT_SIZE_OF_STRING)
    private String name;

    /**
     * Creation date of this model.
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "insert_date")
    private Date insertDate;

    /**
     * Last update date of this model.
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "update_date")
    private Date updateDate;

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
     * Get creation date of this model.
     *
     * @return Date of creation.
     */
    public Date getInsertDate() {
        if (insertDate == null) {
            return null;
        }
        return new Date(this.insertDate.getTime());
    }

    /**
     * Get last update date of this model.
     *
     * @return Update date.
     */
    public Date getUpdateDate() {
        if (this.updateDate == null) {
            return null;
        }
        return new Date(this.updateDate.getTime());
    }

}
