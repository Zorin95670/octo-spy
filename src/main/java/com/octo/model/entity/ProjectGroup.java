package com.octo.model.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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

/**
 * Project group entity.
 *
 * @author Vincent Moittié
 *
 */
@Entity
@Table(name = "project_groups")
public class ProjectGroup extends AbstractEntity {

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_groups_seq")
    @SequenceGenerator(name = "project_groups_seq", sequenceName = "project_groups_gpr_id_seq", allocationSize = 1)
    @Column(name = "gpr_id", updatable = false, nullable = false)
    private Long id;
    /**
     * Group.
     */
    @ManyToOne()
    @JoinColumn(name = "grp_id")
    private Group group;
    /**
     * Project.
     */
    @ManyToOne()
    @JoinColumn(name = "pro_id")
    private Project project;

    /**
     * Set insertDate before persist in repository.
     */
    @PrePersist
    public void prePersist() {
        this.setInsertDate(Timestamp.valueOf(LocalDateTime.now()));
    }

    /**
     * Get id.
     *
     * @return Id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id.
     *
     * @param id
     *            Id.
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Get group.
     *
     * @return Group.
     */
    public Group getGroup() {
        return group;
    }

    /**
     * Set group.
     *
     * @param group
     *            Group.
     */
    public void setGroup(final Group group) {
        this.group = group;
    }

    /**
     * Get project.
     *
     * @return Project.
     */
    public Project getProject() {
        return project;
    }

    /**
     * Set project.
     *
     * @param project
     *            Project.
     */
    public void setProject(final Project project) {
        this.project = project;
    }
}
