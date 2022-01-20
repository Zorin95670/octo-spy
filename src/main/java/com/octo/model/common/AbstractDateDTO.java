package com.octo.model.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

/**
 * Abstract DTO provide insert and update date definition.
 *
 * @author Vincent Moittié
 */
@Data
public class AbstractDateDTO {
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
}
