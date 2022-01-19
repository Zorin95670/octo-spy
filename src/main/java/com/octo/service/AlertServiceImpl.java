package com.octo.service;

import com.octo.model.alert.Alert;
import com.octo.model.common.Constants;
import com.octo.persistence.model.InformationView;
import com.octo.persistence.model.User;
import com.octo.persistence.repository.InformationRepository;
import com.octo.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation of alert service.
 */
@Service
@Transactional
public class AlertServiceImpl implements AlertService {

    /**
     * Pattern to extract first number.
     */
    private static final Pattern EXTRACT_FIRST_NUMBER = Pattern.compile("([a-zA-Z ]*)([0-9]+)(.*)");

    /**
     * User repository.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * User repository.
     */
    @Autowired
    private InformationRepository informationRepository;

    /**
     * Password encoder.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Alert> getAlerts() {
        List<Alert> alerts = new ArrayList<>();

        this.setIsSecureAdministratorAlert(alerts);
        this.setIsValidAdministratorEmailAlert(alerts);
        this.setIsValidDatabaseVersionAlert(alerts);

        return alerts;
    }


    /**
     * Add alert in alerts list to indicate if password is not secure.
     *
     * @param alerts Alerts to populate.
     */
    public void setIsSecureAdministratorAlert(final List<Alert> alerts) {
        Optional<User> opt = userRepository.findByLogin(Constants.DEFAULT_ADMIN_LOGIN);
        if (opt.isEmpty()) {
            return;
        }
        User user = opt.get();

        if (!user.isActive() || !passwordEncoder.matches(Constants.DEFAULT_ADMIN_LOGIN, user.getPassword())) {
            return;
        }
        alerts.add(
                new Alert("critical", "security", "Administrator's password is not secure, please change it."));
    }

    /**
     * Add alert in alerts list to indicate if email is not the default.
     *
     * @param alerts Alerts to populate.
     */
    public void setIsValidAdministratorEmailAlert(final List<Alert> alerts) {
        Optional<User> opt = userRepository.findByLogin(Constants.DEFAULT_ADMIN_LOGIN);
        if (opt.isEmpty()) {
            return;
        }
        User user = opt.get();
        if (!user.isActive() || !"no-reply@change.it".equals(user.getEmail())) {
            return;
        }
        alerts.add(new Alert("warning", "security", "Administrator's email is not set, please change it."));
    }

    /**
     * Add alert in alerts list to indicate if version database is compatible.
     *
     * @param alerts Alerts to populate.
     */
    public void setIsValidDatabaseVersionAlert(final List<Alert> alerts) {
        Optional<InformationView> opt = this.informationRepository.findById(1L);
        if (opt.isEmpty()) {
            return;
        }
        String version = opt.get().getVersion();

        Matcher matcher = EXTRACT_FIRST_NUMBER.matcher(version);
        if (!matcher.find()) {
            return;
        }
        int versionNumber = Integer.parseInt(matcher.group(2));
        if (versionNumber >= Constants.DATABASE_VERSION_MINIMUM
                && versionNumber <= Constants.DATABASE_VERSION_MAXIMUM) {
            return;
        }
        alerts.add(new Alert("warning", "incompatible",
                String.format("Database version, current %s, minimum %s, maximum %s.", versionNumber,
                        Constants.DATABASE_VERSION_MINIMUM, Constants.DATABASE_VERSION_MAXIMUM)));
    }
}
