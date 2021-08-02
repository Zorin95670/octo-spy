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

import com.octo.utils.Constants;

/**
 * Deployment entity.
 *
 * @author Vincent Moittié
 *
 */
@Entity
@Table(name = "deployments")
public class Deployment extends AbstractEntity {

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deployments_seq")
    @SequenceGenerator(name = "deployments_seq", sequenceName = "deployments_dpl_id_seq", allocationSize = 1)
    @Column(name = "dpl_id", updatable = false, nullable = false)
    private Long id;
    /**
     * Deployment's environment.
     */
    @ManyToOne()
    @JoinColumn(name = "env_id")
    private Environment environment;
    /**
     * Deployment's project.
     */
    @ManyToOne()
    @JoinColumn(name = "pro_id")
    private Project project;
    /**
     * Deployed version.
     */
    @Column(name = "version", nullable = false, length = Constants.DEFAULT_SIZE_OF_STRING)
    private String version;
    /**
     * Client.
     */
    @Column(name = "client", length = Constants.DEFAULT_SIZE_OF_STRING)
    private String client;
    /**
     * Is deployment is sill alive.
     */
    @Column(name = "alive")
    private boolean alive;

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
     * Get environment.
     *
     * @return Environment.
     */
    public Environment getEnvironment() {
        return environment;
    }

    /**
     * Set environment.
     *
     * @param environment
     *            Environment.
     */
    public void setEnvironment(final Environment environment) {
        this.environment = environment;
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

    /**
     * Get client.
     *
     * @return Client.
     */
    public String getClient() {
        return client;
    }

    /**
     * Set client.
     *
     * @param client
     *            Client.
     */
    public void setClient(final String client) {
        this.client = client;
    }

    /**
     * Is deployment alive.
     *
     * @return Alive state.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Set deployement alive state.
     *
     * @param alive
     *            Alive state.
     */
    public void setAlive(final boolean alive) {
        this.alive = alive;
    }
}
