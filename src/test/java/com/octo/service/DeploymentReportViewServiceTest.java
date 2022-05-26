package com.octo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;
import com.octo.persistence.repository.DeploymentReportViewRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
class DeploymentReportViewServiceTest {

    @Mock
    DeploymentReportViewRepository deploymentReportViewRepository;

    @InjectMocks
    DeploymentReportViewServiceImpl service;

    @Test
    void testCount() {
        GlobalException exception = null;
        try {
            service.count(Map.of("", ""), List.of("bad"));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(ErrorType.UNKNOWN_FIELD.getMessage(), exception.getMessage());
        assertEquals("field", exception.getError().getField());
        assertEquals("bad", exception.getError().getValue());

        Mockito.when(this.deploymentReportViewRepository.count(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(JsonNodeFactory.instance.objectNode());

        JsonNode node = null;
        exception = null;
        try {
            node = service.count(Map.of("", ""), List.of("client"));
        } catch (GlobalException e) {
            exception = e;
        }
        assertNull(exception);
        assertNotNull(node);
    }
}
