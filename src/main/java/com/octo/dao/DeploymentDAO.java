package com.octo.dao;

import org.springframework.stereotype.Repository;

import com.octo.model.dto.common.DefaultDTO;
import com.octo.model.entity.Deployment;

/**
 * DAO for deployment entity.
 *
 * @author vmoittie
 *
 */
@Repository("deploymentDAO")
public class DeploymentDAO extends CommonDAO<Deployment, DefaultDTO> {

    /**
     * Default constructor.
     */
    public DeploymentDAO() {
        super(Deployment.class);
    }

}
