package com.octo.model.authentication;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

class AuthenticationTypeTest {

    @Test
    void test() {
        assertNotNull(AuthenticationType.LOCAL.getValue());
    }

}
