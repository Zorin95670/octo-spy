package com.octo.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.octo.utils.Constants;

/**
 * Information entity.
 *
 * @author Vincent Moittié
 *
 */
@Entity
@Table(name = "information_view")
public class InformationView {

    /**
     * Primary key.
     */
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    /**
     * Version.
     */
    @Column(name = "version", nullable = false, length = Constants.DEFAULT_SIZE_OF_STRING)
    private String version;

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
     * Get version.
     *
     * @return Version.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Set version.
     *
     * @param version
     *            Version.
     */
    public void setVersion(final String version) {
        this.version = version;
    }

}
