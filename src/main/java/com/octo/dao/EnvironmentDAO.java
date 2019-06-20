package com.octo.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.octo.model.entity.Environment;
import com.octo.model.exception.OctoException;

/**
 * Environment DAO.
 *
 * @author vmoittie
 *
 */
@Repository("environmentDAO")
public class EnvironmentDAO extends CommonDAO<Environment> {

    /**
     * Constructor for Environment's DAO.
     */
    public EnvironmentDAO() {
        super(Environment.class);
    }

    @Override
    public final Environment save(final Environment entity) throws OctoException {
        throw new UnsupportedOperationException();
    }

    /**
     * Get all environments.
     */
    @Override
    public final List<Environment> findAll() {
        CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Environment> criteria = builder.createQuery(getType());
        Root<Environment> rootEntry = criteria.from(getType());
        CriteriaQuery<Environment> all = criteria.select(rootEntry);
        TypedQuery<Environment> allQuery = this.getEntityManager().createQuery(all);
        return allQuery.getResultList();
    }
}
