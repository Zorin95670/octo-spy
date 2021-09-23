package com.octo.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;

class UserTokenTest {

    @Test
    void testGetterAndSetter() {
        UserToken token = new UserToken();

        assertNull(token.getId());
        assertNull(token.getUser());
        assertNull(token.getName());
        assertNull(token.getToken());

        token.setId(1L);
        token.setUser(new User());
        token.setName("name");
        token.setToken("token");

        assertEquals(Long.valueOf(1L), token.getId());
        assertNotNull(token.getUser());
        assertEquals("name", token.getName());
        assertEquals("token", token.getToken());

        token.prePersist();
        assertNotNull(token.getInsertDate());
    }

}
