package com.octo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.octo.controller.model.QueryFilter;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.model.project.ProjectRecord;
import com.octo.persistence.model.Group;
import com.octo.persistence.model.Project;
import com.octo.persistence.model.ProjectGroup;
import com.octo.persistence.model.ProjectView;
import com.octo.persistence.repository.ProjectRepository;
import com.octo.persistence.repository.ProjectViewRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
class ProjectServiceTest {

    @Mock
    ProjectRepository projectRepository;

    @Mock
    ProjectViewRepository projectViewRepository;

    @Mock
    GroupService groupService;

    @InjectMocks
    ProjectServiceImpl service;

    @Test
    void testCount() {
        GlobalException exception = null;
        try {
            service.count(Map.of("", ""), "bad", null);
        }catch (GlobalException e){
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(ErrorType.UNKNOWN_FIELD.getMessage(), exception.getMessage());
        assertEquals("field", exception.getError().getField());
        assertEquals("bad", exception.getError().getValue());

        Mockito.when(this.projectViewRepository.count(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(JsonNodeFactory.instance.objectNode());

        JsonNode node = null;
        exception = null;
        try {
            node = service.count(Map.of("", ""), "name", "test");
        }catch (GlobalException e){
            exception = e;
        }
        assertNull(exception);
        assertNotNull(node);
    }

    @Test
    void testLoad() {
        Mockito.when(this.projectViewRepository.findById(Mockito.any())).thenReturn(Optional.of(new ProjectView()));
        assertNotNull(service.load(1L));
    }

    @Test
    void testSaveDuplicateMasterProject() {
        Mockito.when(this.projectViewRepository
                        .findByNameAndIsMasterIsTrue(Mockito.any()))
                .thenReturn(Optional.of(new ProjectView()));

        GlobalException exception = null;
        try {
            this.service.save(new ProjectRecord("test", null, true, null));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getError().getMessage(), ErrorType.WRONG_VALUE.getMessage());
    }

    @Test
    void testSaveMasterProject() {
        Mockito.when(this.projectViewRepository
                        .findByNameAndIsMasterIsTrue(Mockito.any()))
                .thenReturn(Optional.empty());
        Mockito.when(this.projectRepository.save(Mockito.any())).thenReturn(new Project());
        Mockito.when(this.groupService.save(Mockito.any())).thenReturn(new Group());

        GlobalException exception = null;
        try {
            this.service.save(new ProjectRecord("test", null, true, null));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
    }

    @Test
    void testSaveSubProjectWithEmptyMasterProject() {
        GlobalException exception = null;
        try {
            this.service.save(new ProjectRecord("test", null, false, null));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getError().getMessage(), ErrorType.EMPTY_VALUE.getMessage());
    }

    @Test
    void testSaveDuplicateSubProject() {
        Mockito.when(this.projectViewRepository
                        .findByNameAndMasterProjectAndIsMasterIsFalse(Mockito.any(), Mockito.any()))
                .thenReturn(Optional.of(new ProjectView()));

        GlobalException exception = null;
        try {
            this.service.save(new ProjectRecord("test", null, false, "master"));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getError().getMessage(), ErrorType.WRONG_VALUE.getMessage());
    }

    @Test
    void testSaveSubProjectWithUnknownMasterProject() {
        Mockito.when(this.projectViewRepository
                        .findByNameAndMasterProjectAndIsMasterIsFalse(Mockito.any(), Mockito.any()))
                .thenReturn(Optional.empty());
        GlobalException exception = null;
        try {
            this.service.save(new ProjectRecord("test", null, false, "master"));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getError().getMessage(), ErrorType.ENTITY_NOT_FOUND.getMessage());
    }

    @Test
    void testSaveSubProject() {
        ProjectView master = new ProjectView();
        master.setId(1L);
        Mockito.when(this.projectViewRepository
                        .findByNameAndMasterProjectAndIsMasterIsFalse(Mockito.any(), Mockito.any()))
                .thenReturn(Optional.empty());
        Mockito.when(this.projectViewRepository
                        .findByNameAndIsMasterIsTrue(Mockito.any()))
                .thenReturn(Optional.of(master));
        Mockito.when(this.projectRepository.save(Mockito.any())).thenReturn(new Project());
        Mockito.when(this.groupService.addProjectToGroup(Mockito.anyLong(), Mockito.any()))
                .thenReturn(new ProjectGroup());

        GlobalException exception = null;
        try {
            this.service.save(new ProjectRecord("test", null, false, "master"));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
    }

    @Test
    void testDelete() {
        Mockito.when(this.projectRepository.findById(Mockito.any())).thenReturn(Optional.of(new Project()));
        Mockito.doNothing().when(this.projectRepository).deleteById(Mockito.any());

        GlobalException exception = null;
        try {
            this.service.delete(1L);
        } catch (GlobalException e) {
            exception = e;
        }

        assertNull(exception);
    }

    @Test
    void testFindAll() {
        List<ProjectView> expected = new ArrayList<>();
        Mockito.when(this.projectViewRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(expected));

        assertNotNull(this.service.findAll(Map.of(), new QueryFilter().getPagination()));
    }

    @Test
    void testUpdate() {
        Mockito.when(this.projectRepository.save(Mockito.any())).thenReturn(new Project());
        Mockito.when(this.projectRepository.findById(Mockito.any())).thenReturn(Optional.of(new Project()));
        Exception exception = null;
        try {
            this.service.update(1L, new Project());
        } catch (Exception e) {
            exception = e;
        }

        assertNull(exception);
    }
}
