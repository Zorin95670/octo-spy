package com.octo.persistence.model;

import com.octo.model.common.Constants;
import com.octo.persistence.specification.filter.FilterType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Information entity.
 *
 * @author Vincent Moittié
 */
@Entity
@Table(name = "information_view")
@Data
public class InformationView {

    /**
     * Primary key.
     */
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @FilterType(type = FilterType.Type.NUMBER)
    private Long id;
    /**
     * Version.
     */
    @Column(name = "version", nullable = false, length = Constants.DEFAULT_SIZE_OF_STRING)
    @FilterType(type = FilterType.Type.TEXT)
    private String version;
}
