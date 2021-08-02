package com.octo.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void testGetterAndSetter() {
        User user = new User();

        assertNull(user.getId());
        assertNull(user.getAuthenticationType());
        assertNull(user.getLogin());
        assertNull(user.getFirstname());
        assertNull(user.getLastname());
        assertNull(user.getPassword());
        assertNull(user.getEmail());
        assertFalse(user.isActive());

        user.setId(1L);
        user.setAuthenticationType("authenticationType");
        user.setLogin("login");
        user.setFirstname("firstname");
        user.setLastname("lastname");
        user.setPassword("password");
        user.setEmail("email");
        user.setActive(true);

        assertEquals(Long.valueOf(1L), user.getId());
        assertEquals("authenticationType", user.getAuthenticationType());
        assertEquals("login", user.getLogin());
        assertEquals("password", user.getPassword());
        assertEquals("firstname", user.getFirstname());
        assertEquals("lastname", user.getLastname());
        assertEquals("email", user.getEmail());
        assertTrue(user.isActive());

        user.prePersist();
        assertNotNull(user.getInsertDate());
    }

}
