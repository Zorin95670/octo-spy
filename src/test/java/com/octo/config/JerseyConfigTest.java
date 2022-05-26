package com.octo.config;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

@Tag("unit")
class JerseyConfigTest {


    @Test
    void testUpdatePassword() {
        Exception exception = null;
        try {
            new JerseyConfig();
        } catch (Exception e) {
            exception = e;
        }
        assertNull(exception);
    }
}
