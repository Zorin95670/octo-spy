package com.octo.model.dto.count;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class CountDTOTest {

    @Test
    void test() {
        final CountDTO dto = new CountDTO();
        assertNull(dto.getField());
        assertNull(dto.getValue());

        dto.setField("field");
        dto.setValue("value");

        assertEquals("field", dto.getField());
        assertEquals("value", dto.getValue());
    }

}
