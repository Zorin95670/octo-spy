package com.octo.service;

import com.octo.model.dashboard.DashboardRecord;
import com.octo.persistence.model.Dashboard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Dashboard service.
 */
public interface DashboardService extends ServiceHelper {

    /**
     * Load dashboard by id.
     *
     * @param login Login.
     * @param id    Id.
     * @return Dashboard.
     */
    Dashboard load(String login, Long id);

    /**
     * Save dashboard in database.
     *
     * @param login        Login.
     * @param newDashboard Record to save
     * @return Dashboard.
     */
    Dashboard save(String login, DashboardRecord newDashboard);

    /**
     * Delete an dashboard.
     *
     * @param login Login.
     * @param id    Id of dashboard to update.
     */
    void delete(String login, Long id);

    /**
     * Find dashboards.
     *
     * @param login    Login.
     * @param filters  Filters.
     * @param pageable Page option.
     * @return List of selected dashboards.
     */
    Page<Dashboard> find(String login, Map<String, String> filters, Pageable pageable);

    /**
     * Update dashboard.
     *
     * @param login     Login.
     * @param id        Dashboard's id.
     * @param dashboard Dashboard's information.
     * @throws InvocationTargetException If the property accessor method throws an exception.
     * @throws IllegalAccessException    If the caller does not have access to the property accessor
     *                                   method.
     */
    void update(String login, Long id, DashboardRecord dashboard)
            throws IllegalAccessException, InvocationTargetException;
}
