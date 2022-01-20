package com.octo.service;

import com.octo.model.alert.Alert;
import com.octo.model.common.Constants;
import com.octo.persistence.model.InformationView;
import com.octo.persistence.model.User;
import com.octo.persistence.repository.InformationRepository;
import com.octo.persistence.repository.UserRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
class AlertServiceTest {

    private static final String VERSION = "PostgreSQL %s.3 (Debian 13.3-1.pgdg100+1) "
            + "on x86_64-pc-linux-gnu, compiled by gcc (Debian 8.3.0-6) 8.3.0, 64-bit";
    @Mock
    InformationRepository informationRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @InjectMocks
    AlertServiceImpl service;

    @Test
    void testGetAlertsReturnNoAlert() {
        InformationView entity = new InformationView();
        entity.setVersion("");
        Mockito.when(userRepository.findByLogin(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(informationRepository.findById(Mockito.any())).thenReturn(Optional.of(entity));

        List<Alert> alerts = this.service.getAlerts().stream().toList();
        assertNotNull(alerts);
        assertTrue(alerts.isEmpty());

        entity.setVersion(String.format(VERSION, Constants.DATABASE_VERSION_MINIMUM));
        Mockito.when(userRepository.findByLogin(Mockito.any())).thenReturn(Optional.of(new User()));
        Mockito.when(informationRepository.findById(Mockito.any())).thenReturn(Optional.of(entity));
        alerts = this.service.getAlerts();
        assertNotNull(alerts);
        assertTrue(alerts.isEmpty());

        User user = new User();
        user.setActive(true);
        user.setEmail("test@test.com");
        user.setPassword("test");
        Mockito.when(userRepository.findByLogin(Mockito.any())).thenReturn(Optional.of(user));
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
        Mockito.when(userRepository.findByLogin(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(informationRepository.findById(Mockito.any())).thenReturn(Optional.of(entity));
        Mockito.when(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(true);

        List<Alert> alerts = this.service.getAlerts().stream().toList();
        assertNotNull(alerts);
        assertEquals(3, alerts.size());

        entity.setVersion(String.format(VERSION, Constants.DATABASE_VERSION_MAXIMUM + 1));
        Mockito.when(informationRepository.findById(Mockito.any())).thenReturn(Optional.of(entity));
        alerts = this.service.getAlerts();
        assertNotNull(alerts);
        assertEquals(3, alerts.size());
    }
}
