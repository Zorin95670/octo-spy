package com.octo.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.octo.model.common.Constants;
import com.octo.persistence.specification.filter.FilterType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

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
import java.util.Map;

/**
 * Deployment entity.
 *
 * @author Vincent Moittié
 */
@Entity
@Table(name = "dashboards")
@EqualsAndHashCode(callSuper = true)
@Data
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Dashboard extends AbstractEntity {

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dashboards_seq")
    @SequenceGenerator(name = "dashboards_seq", sequenceName = "dashboards_dbd_id_seq", allocationSize = 1)
    @Column(name = "dbd_id", updatable = false, nullable = false)
    @FilterType(type = FilterType.Type.NUMBER)
    private Long id;
    /**
     * User.
     */
    @ManyToOne()
    @JoinColumn(name = "usr_id")
    @FilterType(type = FilterType.Type.NUMBER)
    @JsonIgnore
    private User user;
    /**
     * Dashboard display name.
     */
    @Column(name = "name", nullable = false, length = Constants.DEFAULT_SIZE_OF_STRING)
    @FilterType(type = FilterType.Type.TEXT)
    private String name;
    /**
     * Query parameters to load the dashboard.
     */
    @Column(name = "parameters", nullable = false)
    @Type(type = "jsonb")
    private Map<String, String> parameters;
    /**
     * Visibility of the dashboard.
     */
    @Column(name = "visible", nullable = false)
    @FilterType(type = FilterType.Type.BOOLEAN)
    private boolean visible;
    /**
     * Able to delete the dashboard.
     */
    @Column(name = "can_be_deleted", nullable = false)
    @FilterType(type = FilterType.Type.BOOLEAN)
    private boolean canBeDeleted;

    /**
     * Set insertDate before persist in repository.
     */
    @PrePersist
    public void prePersist() {
        this.setInsertDate(Timestamp.valueOf(LocalDateTime.now()));
    }
}
