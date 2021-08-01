package com.octo.model.dto.user;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.Test;

class FullUserDTOTest {

    @Test
    void testGetterAndSetter() {
        FullUserDTO user = new FullUserDTO();

        assertNull(user.getUser());
        assertNotNull(user.getRoles());
        assertTrue(user.getRoles().isEmpty());

        user.setUser(new UserViewDTO());
        user.setRoles(Collections.singletonList("a"));

        assertNotNull(user.getUser());
        assertNotNull(user.getRoles());
        assertFalse(user.getRoles().isEmpty());
        assertEquals(1, user.getRoles().size());
        assertEquals("a", user.getRoles().get(0));
    }

}
