package com.octo.persistence.model;

import com.octo.persistence.specification.filter.FilterType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Project group entity.
 *
 * @author Vincent Moittié
 */
@Entity
@Table(name = "project_groups")
@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectGroup extends AbstractEntity {

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_groups_seq")
    @SequenceGenerator(name = "project_groups_seq", sequenceName = "project_groups_gpr_id_seq", allocationSize = 1)
    @Column(name = "gpr_id", updatable = false, nullable = false)
    @FilterType(type = FilterType.Type.NUMBER)
    private Long id;
    /**
     * Group.
     */
    @ManyToOne()
    @JoinColumn(name = "grp_id")
    @FilterType(type = FilterType.Type.NUMBER)
    private Group group;
    /**
     * Project.
     */
    @ManyToOne()
    @JoinColumn(name = "pro_id")
    @FilterType(type = FilterType.Type.NUMBER)
    private Project project;

    /**
     * Set insertDate before persist in repository.
     */
    @PrePersist
    public void prePersist() {
        this.setInsertDate(Timestamp.valueOf(LocalDateTime.now()));
    }
}
