package com.octo.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.octo.model.dto.common.DefaultDTO;
import com.octo.model.entity.DeploymentView;

/**
 * DAO for deployment entity.
 *
 * @author vmoittie
 *
 */
@Repository("deploymentViewDAO")
public class DeploymentViewDAO extends CommonDAO<DeploymentView, DefaultDTO> {

    /**
     * Default constructor.
     */
    public DeploymentViewDAO() {
        super(DeploymentView.class);
    }

    /**
     * Get all deployments.
     */
    @Override
    public final List<DeploymentView> findAll() {
        CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<DeploymentView> criteria = builder.createQuery(getType());
        Root<DeploymentView> rootEntry = criteria.from(getType());
        CriteriaQuery<DeploymentView> all = criteria.select(rootEntry);
        TypedQuery<DeploymentView> allQuery = this.getEntityManager().createQuery(all);
        return allQuery.getResultList();
    }

}
