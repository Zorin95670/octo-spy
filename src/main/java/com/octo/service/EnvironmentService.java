package com.octo.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.octo.dao.IDAO;
import com.octo.model.dto.common.SearchByNameDTO;
import com.octo.model.dto.environment.EnvironmentDTO;
import com.octo.model.dto.environment.NewEnvironmentRecord;
import com.octo.model.entity.Environment;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.utils.bean.BeanMapper;
import com.octo.utils.bean.NullAwareBeanUtilsBean;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * Environment service.
 *
 * @author Vincent Moittié
 *
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
        QueryFilter filter = new QueryFilter();
        filter.setOrder("position");
        filter.setSort("asc");
        return this.environmentDAO.find(filter, true).stream().map(new BeanMapper<>(EnvironmentDTO.class)).toList();
    }

    /**
     * Save environment in database.
     *
     * @param dto
     *            DTO to save
     * @return Environment.
     */
    public EnvironmentDTO save(final NewEnvironmentRecord dto) {
        if (dto == null || StringUtils.isBlank(dto.name())) {
            throw new GlobalException(ErrorType.EMPTY_VALUE, "name", null);
        }

        SearchByNameDTO filter = new SearchByNameDTO(StringUtils.capitalize(dto.name().toLowerCase()));
        Optional<Environment> environment = this.environmentDAO.load(filter);
        if (environment.isPresent()) {
            throw new GlobalException(ErrorType.WRONG_VALUE, "name", "duplicate");
        }

        Environment entity = new BeanMapper<>(Environment.class).apply(dto);
        entity.setName(StringUtils.capitalize(entity.getName().toLowerCase()));

        return new BeanMapper<>(EnvironmentDTO.class).apply(this.environmentDAO.save(entity));
    }

    /**
     * Update environment information.
     *
     * @param id
     *            Environment's id.
     * @param environmentRecord
     *            Environment's information.
     * @throws InvocationTargetException
     *             If the property accessor method throws an exception.
     * @throws IllegalAccessException
     *             If the caller does not have access to the property accessor
     *             method.
     */
    public void update(final Long id, final NewEnvironmentRecord environmentRecord)
            throws IllegalAccessException, InvocationTargetException {
        if ("".equals(StringUtils.trim(environmentRecord.name()))) {
            throw new GlobalException(ErrorType.EMPTY_VALUE, "name");
        }
        if (StringUtils.isNotBlank(environmentRecord.name())) {
            SearchByNameDTO filter = new SearchByNameDTO(
                    StringUtils.capitalize(environmentRecord.name().toLowerCase()));
            Optional<Environment> environment = this.environmentDAO.load(filter);
            if (environment.isPresent()) {
                throw new GlobalException(ErrorType.WRONG_VALUE, "name", "duplicate");
            }
        }
        Environment environment = this.environmentDAO.loadEntityById(id);
        BeanUtilsBean merger = new NullAwareBeanUtilsBean();
        merger.copyProperties(environment, new BeanMapper<>(EnvironmentDTO.class).apply(environmentRecord));
        environment.setName(StringUtils.capitalize(environment.getName().toLowerCase()));

        this.environmentDAO.save(environment);
    }

    /**
     * Delete an environment.
     *
     * @param id
     *            Id of environment.
     */
    public void delete(final Long id) {
        final Environment entity = this.environmentDAO.loadEntityById(id);
        this.environmentDAO.delete(entity);
    }
}
