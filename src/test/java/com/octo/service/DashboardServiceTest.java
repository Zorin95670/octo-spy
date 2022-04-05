package com.octo.service;

import com.octo.controller.model.QueryFilter;
import com.octo.model.dashboard.DashboardRecord;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.persistence.model.Dashboard;
import com.octo.persistence.model.User;
import com.octo.persistence.repository.DashboardRepository;
import com.octo.persistence.repository.UserRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@Tag("unit")
class DashboardServiceTest {

    @Mock
    DashboardRepository dashboardRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    DashboardServiceImpl service;

    @Test
    void testLoad() {
        Dashboard entity = new Dashboard();
        entity.setVisible(true);
        Mockito.when(dashboardRepository.findById(Mockito.any())).thenReturn(Optional.of(entity));

        assertEquals(entity, service.load("test", 1L));

        User user = new User();
        user.setLogin("test");
        entity.setVisible(false);
        entity.setUser(user);
        Mockito.when(dashboardRepository.findById(Mockito.any())).thenReturn(Optional.of(entity));
        assertEquals(entity, service.load("test", 1L));
    }

    @Test
    void testLoadRaiseAnError() {
        Dashboard entity = new Dashboard();
        User user = new User();
        user.setLogin("test");
        entity.setVisible(false);
        entity.setUser(user);
        Mockito.when(dashboardRepository.findById(Mockito.any())).thenReturn(Optional.of(entity));

        GlobalException exception = null;
        try {
            service.load("test1", 1L);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getError().getMessage(), ErrorType.ENTITY_NOT_FOUND.getMessage());
    }

    @Test
    void testSave() {
        DashboardRecord record = new DashboardRecord("test", Map.of("id", "1"), true);
        Dashboard dashboard = new Dashboard();
        Mockito.when(userRepository.findByLogin(Mockito.any())).thenReturn(Optional.of(new User()));
        Mockito.when(dashboardRepository.save(Mockito.any())).thenReturn(dashboard);
        assertEquals(dashboard, service.save("test", record));
    }

    @Test
    void testSaveRaiseAnError() {
        DashboardRecord record = new DashboardRecord("test", Map.of(), true);

        Mockito.when(userRepository.findByLogin(Mockito.any())).thenReturn(Optional.empty());
        GlobalException exception = null;
        try {
            service.save("test1", record);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getError().getMessage(), ErrorType.INTERNAL_ERROR.getMessage());


        Mockito.when(userRepository.findByLogin(Mockito.any())).thenReturn(Optional.of(new User()));
        exception = null;
        try {
            service.save("test1", record);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getError().getMessage(), ErrorType.EMPTY_VALUE.getMessage());
    }

    @Test
    void testDelete() {
        Dashboard entity = new Dashboard();
        User user = new User();
        user.setLogin("test");
        entity.setVisible(false);
        entity.setUser(user);
        entity.setCanBeDeleted(true);
        Mockito.when(dashboardRepository.findById(Mockito.any())).thenReturn(Optional.of(entity));

        GlobalException exception = null;
        try {
            service.delete("test", 1l);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
    }

    @Test
    void testDeleteRaiseAnError() {
        Dashboard entity = new Dashboard();
        User user = new User();
        user.setLogin("test");
        entity.setVisible(false);
        entity.setUser(user);
        entity.setCanBeDeleted(false);
        Mockito.when(dashboardRepository.findById(Mockito.any())).thenReturn(Optional.of(entity));

        GlobalException exception = null;
        try {
            service.delete("test1", 1l);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getError().getMessage(), ErrorType.ENTITY_NOT_FOUND.getMessage());

        exception = null;
        try {
            service.delete("test", 1l);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getError().getMessage(), ErrorType.WRONG_VALUE.getMessage());
    }

    @Test
    void testFind() {
        Map<String, String> filters = new HashMap<>();
        filters.put("visible", "true");
        Mockito.when(this.dashboardRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        assertNotNull(service.find(null, filters, new QueryFilter().getPagination()));

        filters = new HashMap<>();
        filters.put("visible", "false");
        User user = new User();
        user.setId(1L);
        Mockito.when(userRepository.findByLogin(Mockito.any())).thenReturn(Optional.of(user));
        assertNotNull(service.find("test", filters, new QueryFilter().getPagination()));
    }

    @Test
    void testFindRaiseAnError() {
        Map<String, String> filters = new HashMap<>();
        filters.put("visible", "false");

        Mockito.when(userRepository.findByLogin(Mockito.any())).thenReturn(Optional.empty());

        GlobalException exception = null;
        try {
            service.find(null, filters, Pageable.unpaged());
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getError().getMessage(), ErrorType.AUTHORIZATION_ERROR.getMessage());

        exception = null;
        try {
            service.find("test1", filters, Pageable.unpaged());
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getError().getMessage(), ErrorType.INTERNAL_ERROR.getMessage());
    }

    @Test
    void testUpdate() throws InvocationTargetException, IllegalAccessException {
        Dashboard entity = new Dashboard();
        User user = new User();
        user.setLogin("test");
        entity.setVisible(false);
        entity.setUser(user);
        Mockito.when(dashboardRepository.findById(Mockito.any())).thenReturn(Optional.of(entity));

        GlobalException exception = null;
        try {
            service.update("test", 1L, new DashboardRecord("test", Map.of("id", "1"), true));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);

        entity = new Dashboard();
        user = new User();
        user.setLogin("test");
        entity.setVisible(false);
        entity.setUser(user);
        entity.setCanBeDeleted(true);
        Mockito.when(dashboardRepository.findById(Mockito.any())).thenReturn(Optional.of(entity));

        exception = null;
        try {
            service.update("test", 1L, new DashboardRecord("test", Map.of("id", "1"), true));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);

        entity = new Dashboard();
        user = new User();
        user.setLogin("test");
        entity.setVisible(false);
        entity.setUser(user);
        entity.setCanBeDeleted(true);
        Mockito.when(dashboardRepository.findById(Mockito.any())).thenReturn(Optional.of(entity));

        exception = null;
        try {
            service.update("test", 1L, new DashboardRecord("test", Map.of("id", "1"), false));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
    }

    @Test
    void testUpdateRaiseAnError() throws InvocationTargetException, IllegalAccessException {
        Dashboard entity = new Dashboard();
        User user = new User();
        user.setLogin("test");
        entity.setVisible(false);
        entity.setUser(user);
        Mockito.when(dashboardRepository.findById(Mockito.any())).thenReturn(Optional.of(entity));

        GlobalException exception = null;
        try {
            service.update("test1", 1L, null);
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getError().getMessage(), ErrorType.ENTITY_NOT_FOUND.getMessage());

        exception = null;
        try {
            service.update("test", 1L, new DashboardRecord("test", Map.of(), true));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getError().getMessage(), ErrorType.EMPTY_VALUE.getMessage());
    }
}
