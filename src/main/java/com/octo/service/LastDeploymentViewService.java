package com.octo.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.octo.dao.IDAO;
import com.octo.model.dto.deployment.LastDeploymentDTO;
import com.octo.model.dto.deployment.SearchLastDeploymentViewDTO;
import com.octo.model.entity.LastDeploymentView;
import com.octo.utils.bean.BeanMapper;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * Deployment service.
 *
 * @author Vincent Moittié
 *
 */
@Service
@Transactional
public class LastDeploymentViewService {

    /**
     * Deployment's DAO.
     */
    @Autowired
    private IDAO<LastDeploymentView, QueryFilter> lastDeploymentViewDAO;

    /**
     * find last deployments.
     *
     * @return deployments.
     */
    public List<LastDeploymentDTO> find() {
        List<LastDeploymentView> entities = this.lastDeploymentViewDAO.find(null, true);

        return entities.stream().map(new BeanMapper<>(LastDeploymentDTO.class)).collect(Collectors.toList());
    }
}
