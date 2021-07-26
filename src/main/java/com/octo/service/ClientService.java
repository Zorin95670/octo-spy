package com.octo.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.octo.dao.IDAO;
import com.octo.model.entity.Deployment;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * Client service.
 *
 * @author Vincent Moittié
 *
 */
@Service
@Transactional
public class ClientService {

    /**
     * Deployment DAO.
     */
    @Autowired
    private IDAO<Deployment, QueryFilter> deploymentDAO;

    /**
     * Get all distinct client's names.
     *
     * @return List of distinct client's names.
     */
    public List<String> findAll() {
        EntityManager entityManager = deploymentDAO.getEntityManager();
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<String> criteria = builder.createQuery(String.class);
        final Root<Deployment> root = criteria.from(Deployment.class);
        final Expression<String> client = root.get("client");

        criteria.select(client).distinct(true);
        criteria.orderBy(builder.asc(client));

        return entityManager.createQuery(criteria).getResultList();
    }

}
