package com.octo.service;

import com.octo.persistence.repository.DeploymentRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
class ClientServiceTest {

    @Mock
    DeploymentRepository deploymentRepository;

    @InjectMocks
    ClientServiceImpl service;

    @Test
    void testFindALl() {
        Mockito.when(deploymentRepository.findAllDistinctClient(Mockito.any())).thenReturn(new PageImpl(List.of(""),
                Pageable.ofSize(1), 0));
        assertNotNull(this.service.findAll(Pageable.ofSize(1)));
    }
}
