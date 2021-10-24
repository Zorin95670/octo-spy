package com.octo.model.dto.count;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

class MultipleCountDTOTest {

    @Test
    void test() {
        final MultipleCountDTO dto = new MultipleCountDTO();
        assertNull(dto.getFields());

        dto.setFields(List.of("field"));

        assertEquals(List.of("field"), dto.getFields());
    }

}
