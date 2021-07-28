package com.octo.model.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.octo.utils.json.ToJsonMapper;

class DefaultDTOTest {

    class TestDTO extends DefaultDTO {
        private String name;

        public String getName() {
            return name;
        }

        void setName(String name) {
            this.name = name;
        }
    }

    @Test
    void testToString() {
        assertEquals(new ToJsonMapper<>(false).apply(new TestDTO()), new TestDTO().toString());
        assertNotNull(new TestDTO().getFilters(null));
        assertFalse(new TestDTO().isSpecificFilter(null));
        assertNull(new TestDTO().getSpecificFilter(null, null));
    }
}
