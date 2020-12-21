package com.octo.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cji.dao.IDAO;
import com.cji.utils.bean.BeanMapper;
import com.cji.utils.predicate.filter.QueryFilter;
import com.octo.model.dto.environment.EnvironmentDTO;
import com.octo.model.entity.Environment;

/**
 * Environment service.
 */
@Service
@Transactional
public class EnvironmentService {

    /**
     * Environment's DAO.
     */
    @Autowired
    private IDAO<Environment, QueryFilter> environmentDAO;

    /**
     * Get all environment.
     *
     * @return List of environment.
     */
    public List<EnvironmentDTO> findAll() {
        return this.environmentDAO.find(null, true).stream().map(new BeanMapper<>(EnvironmentDTO.class))
                .collect(Collectors.toList());
    }
}
