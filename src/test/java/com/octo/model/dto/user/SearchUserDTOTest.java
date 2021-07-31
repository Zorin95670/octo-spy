package com.octo.model.dto.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;

class SearchUserDTOTest {

    @Test
    void testGetterAndSetter() {
        SearchUserDTO user = new SearchUserDTO();

        assertNull(user.getId());
        assertNull(user.getAuthenticationType());
        assertNull(user.getLogin());
        assertNull(user.getFirstname());
        assertNull(user.getLastname());
        assertNull(user.getEmail());
        assertNull(user.getActive());

        user.setId("id");
        user.setAuthenticationType("authenticationType");
        user.setLogin("login");
        user.setFirstname("firstname");
        user.setLastname("lastname");
        user.setEmail("email");
        user.setActive("active");

        assertEquals("id", user.getId());
        assertEquals("authenticationType", user.getAuthenticationType());
        assertEquals("login", user.getLogin());
        assertEquals("firstname", user.getFirstname());
        assertEquals("lastname", user.getLastname());
        assertEquals("email", user.getEmail());
        assertEquals("active", user.getActive());
    }

}
