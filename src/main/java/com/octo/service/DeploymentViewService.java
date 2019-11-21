package com.octo.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.octo.dao.IDAO;
import com.octo.model.dto.common.DefaultDTO;
import com.octo.model.dto.deployment.LastDeploymentDTO;
import com.octo.model.entity.DeploymentView;
import com.octo.utils.mapper.BeanMapper;

/**
 * Deployment service.
 *
 * @author vmoittie
 *
 */
@Service
@Transactional
public class DeploymentViewService {

    /**
     * Deployment's DAO.
     */
    @Autowired
    private IDAO<DeploymentView, DefaultDTO> deploymentViewDAO;

    /**
     * find last deployments.
     *
     * @return deployments.
     */
    public List<LastDeploymentDTO> find() {
        List<DeploymentView> entities = this.deploymentViewDAO.findAll();

        return entities.stream().map(new BeanMapper<DeploymentView, LastDeploymentDTO>(LastDeploymentDTO.class))
                .collect(Collectors.toList());
    }
}
