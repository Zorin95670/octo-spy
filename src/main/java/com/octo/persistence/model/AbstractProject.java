package com.octo.persistence.model;

import com.octo.model.common.Constants;
import com.octo.persistence.specification.filter.FilterType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Abstract project entity.
 *
 * @author vmoittie
 */
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AbstractProject extends AbstractEntity {

    /**
     * Primary key.
     */
    @Id
    @SequenceGenerator(name = "projects_seq", sequenceName = "projects_pro_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "projects_seq")
    @Column(name = "pro_id", updatable = false, nullable = false)
    @FilterType(type = FilterType.Type.NUMBER)
    private Long id;
    /**
     * Project's name.
     */
    @Column(name = "name", nullable = false, length = Constants.DEFAULT_SIZE_OF_STRING)
    @FilterType(type = FilterType.Type.TEXT)
    private String name;
    /**
     * Project's color.
     */
    @Column(name = "color", length = Constants.DEFAULT_SIZE_OF_STRING)
    @FilterType(type = FilterType.Type.TEXT)
    private String color;

    /**
     * Set insertDate before persist in repository.
     */
    @PrePersist
    public void prePersist() {
        this.setInsertDate(Timestamp.valueOf(LocalDateTime.now()));
    }
}
