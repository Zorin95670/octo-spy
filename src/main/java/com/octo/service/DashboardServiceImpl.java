package com.octo.service;

import com.octo.model.dashboard.DashboardRecord;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.persistence.model.Dashboard;
import com.octo.persistence.repository.DashboardRepository;
import com.octo.persistence.repository.UserRepository;
import com.octo.persistence.specification.SpecificationHelper;
import com.octo.utils.bean.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Implementation of dashboard service.
 */
@Service
@Transactional
public class DashboardServiceImpl implements DashboardService {

    /**
     * Dashboard repository.
     */
    @Autowired
    private DashboardRepository dashboardRepository;

    /**
     * User repository.
     */
    @Autowired
    private UserRepository userRepository;

    @Override
    public Dashboard load(final String login, final Long id) {
        Dashboard entity = this.loadEntityById(dashboardRepository, id);

        if (entity.isVisible() || entity.getUser().getLogin().equals(login)) {
            return entity;
        }
        throw new GlobalException(ErrorType.ENTITY_NOT_FOUND, "id", String.valueOf(id));
    }

    @Override
    public Dashboard save(final String login, final DashboardRecord newDashboard) {
        Dashboard dashboard = new BeanMapper<>(Dashboard.class).apply(newDashboard);
        dashboard.setUser(userRepository.findByLogin(login)
                .orElseThrow(() -> new GlobalException(ErrorType.INTERNAL_ERROR, "user")));
        dashboard.setCanBeDeleted(true);

        if (dashboard.getParameters().isEmpty()) {
            throw new GlobalException(ErrorType.EMPTY_VALUE, "parameters");
        }

        return dashboardRepository.save(dashboard);
    }

    @Override
    public void delete(final String login, final Long id) {
        Dashboard entity = this.loadEntityById(dashboardRepository, id);
        if (!entity.getUser().getLogin().equals(login)) {
            throw new GlobalException(ErrorType.ENTITY_NOT_FOUND, "id", String.valueOf(id));
        }
        if (!entity.isCanBeDeleted()) {
            throw new GlobalException(ErrorType.WRONG_VALUE, "canBeDeleted", "false");
        }

        this.dashboardRepository.delete(entity);
    }

    @Override
    public Page<Dashboard> find(final String login, final Map<String, String> filters, final Pageable pageable) {
        final String key = "visible";
        filters.putIfAbsent(key, "true");
        Boolean isVisible = Boolean.valueOf(filters.get(key));
        if (Boolean.TRUE.equals(isVisible)) {
            filters.remove("user");
        } else {
            this.setFiltersForPrivateDashboard(login, filters);
        }
        return this.dashboardRepository.findAll(new SpecificationHelper<>(Dashboard.class, filters), pageable);
    }

    /**
     * Set user in filters only f user is not null.
     *
     * @param login   Login.
     * @param filters Search filters.
     */
    public void setFiltersForPrivateDashboard(final String login, final Map<String, String> filters) {
        if (login == null) {
            throw new GlobalException(ErrorType.AUTHORIZATION_ERROR, "not authenticated");
        }
        filters.put("user", userRepository.findByLogin(login)
                .orElseThrow(() -> new GlobalException(ErrorType.INTERNAL_ERROR, "user"))
                .getId().toString());
    }

    @Override
    public void update(final String login, final Long id, final DashboardRecord dashboard)
            throws IllegalAccessException, InvocationTargetException {
        Dashboard entity = this.loadEntityById(dashboardRepository, id);
        if (!entity.getUser().getLogin().equals(login)) {
            throw new GlobalException(ErrorType.ENTITY_NOT_FOUND, "id", String.valueOf(id));
        }
        if (dashboard.parameters().isEmpty()) {
            throw new GlobalException(ErrorType.EMPTY_VALUE, "parameters");
        }

        entity.setVisible(!entity.isCanBeDeleted() || dashboard.visible());
        entity.setParameters(dashboard.parameters());
        entity.setName(dashboard.name());

        dashboardRepository.save(entity);
    }
}
