package com.octo.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.octo.dao.IDAO;
import com.octo.model.dto.common.DefaultDTO;
import com.octo.model.dto.environment.EnvironmentDTO;
import com.octo.model.entity.Environment;
import com.octo.utils.mapper.environment.EnvironmentDTOMapper;

/**
 * Environment service.
 *
 * @author vmoittie
 *
 */
@Service
@Transactional
public class EnvironmentService {

    /**
     * Environment's DAO.
     */
    @Autowired
    private IDAO<Environment, DefaultDTO> environmentDAO;

    /**
     * Get all environment.
     *
     * @return List of environment.
     */
    public List<EnvironmentDTO> findAll() {
        return this.environmentDAO.findAll().stream().map(new EnvironmentDTOMapper()).collect(Collectors.toList());
    }
}
