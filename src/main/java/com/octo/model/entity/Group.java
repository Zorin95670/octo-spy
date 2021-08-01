package com.octo.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Group entity.
 *
 * @author Vincent Moittié
 *
 */
@Entity
@Table(name = "groups")
public class Group extends AbstractEntity implements IPrePersistance {

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groups_seq")
    @SequenceGenerator(name = "groups_seq", sequenceName = "groups_grp_id_seq", allocationSize = 1)
    @Column(name = "grp_id", updatable = false, nullable = false)
    private Long id;
    /**
     * Master's project.
     */
    @ManyToOne()
    @JoinColumn(name = "pro_id")
    private Project masterProject;

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
     * Get master's project.
     *
     * @return Master's project.
     */
    public Project getMasterProject() {
        return masterProject;
    }

    /**
     * Set master's project.
     *
     * @param masterProject
     *            Master's Project.
     */
    public void setMasterProject(final Project masterProject) {
        this.masterProject = masterProject;
    }
}
