package com.octo.model.dto.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;

public class ProjectInformationTest {

    @Test
    public void test() {
        ProjectInformation version = new ProjectInformation(null, null, null, null);

        assertNull(version.getProject());
        assertNull(version.getVersion());
        assertNull(version.getEnvironment());
        assertNull(version.getClient());

        version = new ProjectInformation("project", "version", "env", "client");
        assertEquals("project", version.getProject());
        assertEquals("version", version.getVersion());
        assertEquals("env", version.getEnvironment());
        assertEquals("client", version.getClient());
    }
}
