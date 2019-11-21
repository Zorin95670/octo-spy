package com.octo.utils.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Test;

import com.octo.model.dto.environment.EnvironmentDTO;
import com.octo.model.entity.Environment;
import com.octo.model.exception.OctoException;

public class BeanMapperTest {

    @Test
    public void testApply() {
        final BeanMapper<Environment, EnvironmentDTO> mapper = new BeanMapper<>(EnvironmentDTO.class);

        final Environment entity = new Environment();
        entity.setId(1L);
        final EnvironmentDTO dto = mapper.apply(entity);

        assertEquals(Long.valueOf(1L), dto.getId());
    }

    @Test
    public void testWithIgnoreFields() {
        final BeanMapper<EnvironmentDTO, Environment> mapper = new BeanMapper<>(Environment.class, "name");

        final EnvironmentDTO dto = new EnvironmentDTO();
        dto.setId(1L);
        dto.setName("test");

        final Environment value = mapper.apply(dto);
        final Environment expected = new Environment();
        expected.setId(1L);

        assertTrue(EqualsBuilder.reflectionEquals(expected, value));
    }

    @Test
    public void testException() throws NoSuchFieldException, SecurityException {
        class Source {
            @SuppressWarnings("unused")
            public String test1;
        }
        class Destination {
            @SuppressWarnings("unused")
            public String test2;
        }
        class Fail {
            @SuppressWarnings("unused")
            public Fail(final String test) {

            }
        }

        final BeanMapper<Source, Destination> mapper = new BeanMapper<>(Destination.class);

        OctoException exception = null;
        try {
            mapper.setFieldValue(Source.class.getDeclaredField("test1"), Source.class.getDeclaredField("test1"),
                    new Destination(), new Environment());
        } catch (final OctoException e) {
            exception = e;
        }
        assertNotNull(exception);

        exception = null;
        try {
            new BeanMapper<Source, Fail>(Fail.class).init();
        } catch (final OctoException e) {
            exception = e;
        }
        assertNotNull(exception);
    }
}
