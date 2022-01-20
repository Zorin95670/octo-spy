package com.octo.persistence.model;

import com.octo.persistence.specification.filter.FilterType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.sql.Timestamp;

/**
 * Abstract entity with default field (InsertDate and UpdateDate).
 *
 * @author Vincent Moittié
 */
@MappedSuperclass
@Data
public class AbstractEntity {

    /**
     * The creation date of this row.
     */
    @Column(name = "insert_date", updatable = false)
    @FilterType(type = FilterType.Type.DATE)
    private Timestamp insertDate;
    /**
     * The last update date of this row.
     */
    @Column(name = "update_date")
    @Version
    @FilterType(type = FilterType.Type.DATE)
    private Timestamp updateDate;
}
