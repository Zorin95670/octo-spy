package com.octo.utils.bean;

import com.octo.model.deployment.DeploymentDTO;
import com.octo.model.error.GlobalException;
import com.octo.persistence.model.Deployment;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class BeanMapperTest {

    @Test
    void testApply() {
        final BeanMapper<Deployment, DeploymentDTO> mapper = new BeanMapper<>(DeploymentDTO.class);

        final Deployment entity = new Deployment();
        entity.setId(1L);
        DeploymentDTO dto = mapper.apply(entity);

        assertEquals(1L, dto.getId());

        dto = mapper.apply(null);
        assertNotNull(dto);
    }

    @Test
    void testWithIgnoreFields() {
        final BeanMapper<Deployment, Deployment> mapper = new BeanMapper<>(Deployment.class, "version");

        final Deployment dto = new Deployment();
        dto.setId(1L);
        dto.setVersion("test");

        final Deployment value = mapper.apply(dto);
        final Deployment expected = new Deployment();
        expected.setId(1L);

        assertTrue(EqualsBuilder.reflectionEquals(expected, value));
    }

    @Test
    void testException() throws NoSuchFieldException, SecurityException {
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

        GlobalException exception = null;
        try {
            mapper.setFieldValue(Source.class.getDeclaredField("test1"), Source.class.getDeclaredField("test1"),
                    new Destination(), new Deployment());
        } catch (final GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);

        exception = null;
        try {
            new BeanMapper<Source, Fail>(Fail.class).init();
        } catch (final GlobalException e) {
            exception = e;
        }
        assertNotNull(exception);
    }
}
