package com.octo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.octo.dao.IDAO;
import com.octo.model.dto.alert.AlertRecord;
import com.octo.model.dto.user.SearchUserDTO;
import com.octo.model.entity.InformationView;
import com.octo.model.entity.User;
import com.octo.utils.Constants;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * Alert service.
 */
@Service
@Transactional
public class AlertService {

    /**
     * User DAO.
     */
    @Autowired
    private IDAO<User, QueryFilter> userDAO;

    /**
     * User DAO.
     */
    @Autowired
    private IDAO<InformationView, QueryFilter> informationDAO;

    /**
     * Password encoder.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Get all active application alerts.
     *
     * @return Alerts.
     */
    public List<AlertRecord> getAlerts() {
        List<AlertRecord> alerts = new ArrayList<>();

        this.setIsSecureAdministratorAlert(alerts);
        this.setIsValidAdministratorEmailAlert(alerts);
        this.setIsValidDatabaseVersionAlert(alerts);

        return alerts;
    }

    /**
     * Add alert in alerts list to indicate if password is not secure.
     *
     * @param alerts
     *            Alerts to populate.
     */
    public void setIsSecureAdministratorAlert(final List<AlertRecord> alerts) {
        SearchUserDTO filter = new SearchUserDTO();
        filter.setLogin(Constants.DEFAULT_ADMIN_LOGIN);
        Optional<User> opt = userDAO.load(filter);
        if (!opt.isPresent()) {
            return;
        }
        User user = opt.get();

        if (!user.isActive() || !passwordEncoder.matches(Constants.DEFAULT_ADMIN_LOGIN, user.getPassword())) {
            return;
        }
        alerts.add(
                new AlertRecord("critical", "security", "Administrator's password is not secure, please change it."));
    }

    /**
     * Add alert in alerts list to indicate if email is not the default.
     *
     * @param alerts
     *            Alerts to populate.
     */
    public void setIsValidAdministratorEmailAlert(final List<AlertRecord> alerts) {
        SearchUserDTO filter = new SearchUserDTO();
        filter.setLogin(Constants.DEFAULT_ADMIN_LOGIN);
        Optional<User> opt = userDAO.load(filter);
        if (!opt.isPresent()) {
            return;
        }
        User user = opt.get();
        if (!user.isActive() || !"no-reply@change.it".equals(user.getEmail())) {
            return;
        }
        alerts.add(new AlertRecord("warning", "security", "Administrator's email is not set, please change it."));
    }

    /**
     * Add alert in alerts list to indicate if version database is compatible.
     *
     * @param alerts
     *            Alerts to populate.
     */
    public void setIsValidDatabaseVersionAlert(final List<AlertRecord> alerts) {
        String version = this.informationDAO.loadById(1L).getVersion();

        Pattern pattern = Pattern.compile("([a-zA-Z ]*)([0-9]+)(.*)");
        Matcher matcher = pattern.matcher(version);
        if (!matcher.find()) {
            return;
        }
        int versionNumber = Integer.parseInt(matcher.group(2));
        if (versionNumber >= Constants.DATABASE_VERSION_MINIMUM
                && versionNumber <= Constants.DATABASE_VERSION_MAXIMUM) {
            return;
        }
        alerts.add(new AlertRecord("warning", "incompatible",
                String.format("Database version, current %s, mininum %s, maximum %s.", versionNumber,
                        Constants.DATABASE_VERSION_MINIMUM, Constants.DATABASE_VERSION_MAXIMUM)));
    }
}
