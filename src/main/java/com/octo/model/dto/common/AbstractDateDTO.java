package com.octo.model.dto.common;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.octo.model.common.DefaultDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Abstract DTO provide insert and update date definition.
 *
 * @author Vincent Moittié
 *
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public @Data class AbstractDateDTO extends DefaultDTO {
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
