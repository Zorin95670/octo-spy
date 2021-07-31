package com.octo.model.dto.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.Instant;

import org.junit.jupiter.api.Test;

class UserViewDTOTest {

    @Test
    void testGetterAndSetter() {
        UserViewDTO user = new UserViewDTO();

        assertNull(user.getLogin());
        assertNull(user.getFirstname());
        assertNull(user.getLastname());
        assertNull(user.getEmail());
        assertFalse(user.isActive());
        assertNull(user.getInsertDate());
        assertNull(user.getUpdateDate());

        user.setLogin("login");
        user.setFirstname("firstname");
        user.setLastname("lastname");
        user.setEmail("email");
        user.setActive(true);
        user.setInsertDate(Timestamp.from(Instant.ofEpochMilli(1L)));
        user.setUpdateDate(Timestamp.from(Instant.ofEpochMilli(2L)));

        assertEquals("login", user.getLogin());
        assertEquals("firstname", user.getFirstname());
        assertEquals("lastname", user.getLastname());
        assertEquals("email", user.getEmail());
        assertTrue(user.isActive());
        assertEquals(Timestamp.from(Instant.ofEpochMilli(1L)), user.getInsertDate());
        assertEquals(Timestamp.from(Instant.ofEpochMilli(2L)), user.getUpdateDate());

        user.setInsertDate(null);
        user.setUpdateDate(null);
        assertNull(user.getInsertDate());
        assertNull(user.getUpdateDate());
    }
}
