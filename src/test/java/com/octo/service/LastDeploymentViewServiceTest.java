package com.octo.service;

import com.octo.controller.model.QueryFilter;
import com.octo.persistence.model.LastDeploymentView;
import com.octo.persistence.repository.LastDeploymentViewRepository;
import com.octo.persistence.specification.SpecificationHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@Tag("unit")
class LastDeploymentViewServiceTest {

    @Mock
    LastDeploymentViewRepository lastDeploymentViewRepository;

    @InjectMocks
    LastDeploymentViewServiceImpl service;

    @Test
    void testFindAll() {
        List<LastDeploymentView> list = new ArrayList<>();
        Mockito.when(this.lastDeploymentViewRepository.findAll(Mockito.any(SpecificationHelper.class),
                Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(list));
        assertNotNull(service.find(Map.of(), new QueryFilter().getPagination()));
    }

}
