package com.octo.service;

import com.octo.model.alert.Alert;

import java.util.List;

/**
 * Alert service.
 */
public interface AlertService {

    /**
     * Get all active application alerts.
     *
     * @return Alerts.
     */
    List<Alert> getAlerts();
}
