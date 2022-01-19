package com.octo.service;

import com.octo.model.environment.EnvironmentRecord;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.persistence.model.Environment;
import com.octo.persistence.repository.EnvironmentRepository;
import com.octo.persistence.specification.SpecificationHelper;
import com.octo.utils.bean.BeanMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of environment service.
 */
@Service
@Transactional
public class EnvironmentServiceImpl implements EnvironmentService {

    /**
     * Environment's DAO.
     */
    @Autowired
    private EnvironmentRepository environmentRepository;

    @Override
    public Page<Environment> findAll(final Map<String, String> filters, final Pageable pageable) {
        return this.environmentRepository.findAll(new SpecificationHelper<>(Environment.class, filters), PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "position"))));
    }

    @Override
    public Environment save(final EnvironmentRecord newEnvironment) {
        Optional<Environment> environment = this.environmentRepository.findByNameIgnoreCase(newEnvironment.name());
        if (environment.isPresent()) {
            throw new GlobalException(ErrorType.WRONG_VALUE, "name", "duplicate");
        }

        Environment entity = new BeanMapper<>(Environment.class).apply(newEnvironment);
        entity.setName(StringUtils.capitalize(entity.getName().toLowerCase()));

        return this.environmentRepository.save(entity);
    }

    @Override
    public void update(final Long id, final EnvironmentRecord newEnvironment) {
        Optional<Environment> opt = this.environmentRepository
                .findByNameIgnoreCaseAndIdIsNot(newEnvironment.name(), id);
        if (opt.isPresent()) {
            throw new GlobalException(ErrorType.WRONG_VALUE, "name", "duplicate");
        }
        Environment environment = this.loadEntityById(environmentRepository, id);
        environment.setName(StringUtils.capitalize(StringUtils.lowerCase(newEnvironment.name())));
        environment.setPosition(newEnvironment.position());

        this.environmentRepository.save(environment);
    }

    @Override
    public void delete(final Long id) {
        this.loadEntityById(environmentRepository, id);
        this.environmentRepository.deleteById(id);
    }
}
