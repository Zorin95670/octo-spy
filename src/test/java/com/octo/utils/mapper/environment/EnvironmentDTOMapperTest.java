package com.octo.utils.mapper.environment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.octo.model.dto.environment.EnvironmentDTO;
import com.octo.model.entity.Environment;

public class EnvironmentDTOMapperTest {

    @Test
    public void applyTest() {
        EnvironmentDTOMapper mapper = new EnvironmentDTOMapper();

        assertNull(mapper.apply(null));

        EnvironmentDTO dto = mapper.apply(new Environment());
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getName());

        Environment entity = new Environment();
        entity.setId(1L);
        entity.setName("name");

        dto = mapper.apply(entity);
        assertNotNull(dto);
        assertEquals(Long.valueOf(1L), dto.getId());
        assertEquals("name", dto.getName());
    }
}
