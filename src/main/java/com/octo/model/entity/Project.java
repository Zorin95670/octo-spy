package com.octo.model.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import com.octo.utils.Constants;

/**
 * Project model.
 */
@Entity
@Table(name = "projects")
public class Project {

    /**
     * Primary key.
     */
    @Id
    @SequenceGenerator(name = "projects_seq", sequenceName = "projects_pro_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "projects_seq")
    @Column(name = "pro_id", updatable = false, nullable = false)
    private Long id;
    /**
     * Project's name.
     */
    @Column(name = "name", nullable = false, length = Constants.DEFAULT_SIZE_OF_STRING)
    private String name;
    /**
     * The creation date of this row.
     */
    @Column(name = "insert_date", updatable = false)
    private Timestamp insertDate;
    /**
     * The last update date of this row.
     */
    @Column(name = "update_date")
    @Version
    private Timestamp updateDate;

    /**
     * Set insertDate before persist in repository.
     */
    @PrePersist
    public void prePersist() {
        this.setInsertDate(Timestamp.valueOf(LocalDateTime.now()));
    }

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
     * Get project's name.
     *
     * @return Project's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set project's name.
     *
     * @param name
     *            Project's name.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Get the creation date of this entity.
     *
     * @return Creation date.
     */
    public Timestamp getInsertDate() {
        if (this.insertDate == null) {
            return null;
        }
        return Timestamp.from(insertDate.toInstant());
    }

    /**
     * Set the creation date of this entity.
     *
     * @param insertDate
     *            Creation date.
     */
    public void setInsertDate(final Timestamp insertDate) {
        if (insertDate == null) {
            this.insertDate = null;
            return;
        }
        this.insertDate = Timestamp.from(insertDate.toInstant());
    }

    /**
     * Get the last update date of this entity.
     *
     * @return Last update date.
     */
    public Timestamp getUpdateDate() {
        if (this.updateDate == null) {
            return null;
        }
        return Timestamp.from(updateDate.toInstant());
    }

    /**
     * Set the last update date of this entity.
     *
     * @param updateDate
     *            Last update date.
     */
    public void setUpdateDate(final Timestamp updateDate) {
        if (updateDate == null) {
            this.updateDate = null;
            return;
        }
        this.updateDate = Timestamp.from(updateDate.toInstant());
    }

}
