package com.octo.model.dto.project;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.octo.model.common.DefaultDTO;

/**
 * Project DTO.
 */
public class ProjectDTO extends DefaultDTO {
    /**
     * Primary key.
     */
    private Long id;
    /**
     * Project's name.
     */
    private String name;
    /**
     * The creation date of this row.
     */
    @JsonFormat(pattern = "YYYY/MM/DD HH:mm:ss")
    private Timestamp insertDate;
    /**
     * The last update date of this row.
     */
    @JsonFormat(pattern = "YYYY/MM/DD HH:mm:ss")
    private Timestamp updateDate;

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
