package com.octo.model.dto.user.token;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;

class SearchUserTokenDTOTest {

    @Test
    void testGetterAndSetter() {
        SearchUserTokenDTO userToken = new SearchUserTokenDTO();

        assertNull(userToken.getToken());
        assertNull(userToken.getUser());
        assertNull(userToken.getName());

        userToken.setToken("token");
        userToken.setUser("user");
        userToken.setName("name");

        assertEquals("token", userToken.getToken());
        assertEquals("user", userToken.getUser());
        assertEquals("name", userToken.getName());
    }

}
