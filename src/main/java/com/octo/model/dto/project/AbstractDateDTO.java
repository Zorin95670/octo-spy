package com.octo.model.dto.project;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.octo.model.common.DefaultDTO;

/**
 * Abstract DTO provide insert and update date definition.
 *
 * @author Vincent Moittié
 *
 */
public class AbstractDateDTO extends DefaultDTO {
    /**
     * The creation date of this row.
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp insertDate;
    /**
     * The last update date ot this row.
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp updateDate;

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
