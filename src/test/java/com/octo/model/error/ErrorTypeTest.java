package com.octo.model.error;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ErrorTypeTest {

    @Test
    void enumTest() {
        assertEquals("Field value is empty.", ErrorType.EMPTY_VALUE.getMessage());
        assertEquals("Field contains a wrong operator.", ErrorType.WRONG_FILTER_OPERATOR.getMessage());
    }
}
