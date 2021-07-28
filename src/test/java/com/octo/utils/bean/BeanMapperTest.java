package com.octo.utils.bean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Test;

import com.octo.helpers.EntityHelpers;
import com.octo.helpers.EntityTestSearch;
import com.octo.model.error.GlobalException;

class BeanMapperTest {

    @Test
    void testApply() {
        final BeanMapper<EntityHelpers, EntityTestSearch> mapper = new BeanMapper<>(EntityTestSearch.class);

        final EntityHelpers entity = new EntityHelpers();
        entity.setId(1L);
        EntityTestSearch dto = mapper.apply(entity);

        assertEquals("1", dto.getId());

        dto = mapper.apply(null);
        assertNotNull(dto);
    }

    @Test
    void testWithIgnoreFields() {
        final BeanMapper<EntityHelpers, EntityHelpers> mapper = new BeanMapper<>(EntityHelpers.class, "name");

        final EntityHelpers dto = new EntityHelpers();
        dto.setId(1L);
        dto.setName("name");

        final EntityHelpers value = mapper.apply(dto);
        final EntityHelpers expected = new EntityHelpers();
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
                    new Destination(), new EntityHelpers());
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
