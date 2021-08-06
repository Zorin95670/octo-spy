package com.octo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.octo.dao.IDAO;
import com.octo.model.dto.alert.AlertRecord;
import com.octo.model.entity.InformationView;
import com.octo.model.entity.User;
import com.octo.utils.Constants;
import com.octo.utils.predicate.filter.QueryFilter;

@ExtendWith(MockitoExtension.class)
class AlertServiceTest {

    @Mock
    IDAO<InformationView, QueryFilter> informationDAO;

    @Mock
    IDAO<User, QueryFilter> userDAO;

    @Mock
    IGroupService groupService;

    @Mock
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @InjectMocks
    AlertService service;

    private static final String VERSION = "PostgreSQL %s.3 (Debian 13.3-1.pgdg100+1) "
            + "on x86_64-pc-linux-gnu, compiled by gcc (Debian 8.3.0-6) 8.3.0, 64-bit";

    @Test
    void testGetAlertsReturnNoAlert() {
        InformationView entity = new InformationView();
        entity.setVersion("");
        Mockito.when(userDAO.load(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(informationDAO.loadById(Mockito.any())).thenReturn(entity);

        List<AlertRecord> alerts = this.service.getAlerts();
        assertNotNull(alerts);
        assertTrue(alerts.isEmpty());

        entity.setVersion(String.format(VERSION, Constants.DATABASE_VERSION_MINIMUM));
        Mockito.when(userDAO.load(Mockito.any())).thenReturn(Optional.of(new User()));
        Mockito.when(informationDAO.loadById(Mockito.any())).thenReturn(entity);
        alerts = this.service.getAlerts();
        assertNotNull(alerts);
        assertTrue(alerts.isEmpty());

        User user = new User();
        user.setActive(true);
        user.setEmail("test@test.com");
        user.setPassword("test");
        Mockito.when(userDAO.load(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(false);
        alerts = this.service.getAlerts();
        assertNotNull(alerts);
        assertTrue(alerts.isEmpty());
    }

    @Test
    void testGetAlertsReturnFullAlert() {
        InformationView entity = new InformationView();
        entity.setVersion(String.format(VERSION, Constants.DATABASE_VERSION_MINIMUM - 1));

        User user = new User();
        user.setActive(true);
        user.setEmail("no-reply@change.it");
        user.setPassword("test");
        Mockito.when(userDAO.load(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(informationDAO.loadById(Mockito.any())).thenReturn(entity);
        Mockito.when(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(true);

        List<AlertRecord> alerts = this.service.getAlerts();
        assertNotNull(alerts);
        assertEquals(3, alerts.size());

        entity.setVersion(String.format(VERSION, Constants.DATABASE_VERSION_MAXIMUM + 1));
        Mockito.when(informationDAO.loadById(Mockito.any())).thenReturn(entity);
        alerts = this.service.getAlerts();
        assertNotNull(alerts);
        assertEquals(3, alerts.size());
    }
}
