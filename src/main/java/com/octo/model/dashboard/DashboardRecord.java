package com.octo.model.dashboard;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * DTO to call on create deployment.
 *
 * @param name       Dashboard display name.
 * @param parameters Query parameters to load the dashboard.
 * @param visible    Visibility of the dashboard.
 * @author Vincent Moittié
 */
public record DashboardRecord(
        @NotBlank String name,
        @NotNull Map<String, String> parameters,
        boolean visible) {
}
