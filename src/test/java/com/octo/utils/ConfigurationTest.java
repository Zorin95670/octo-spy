package com.octo.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
class ConfigurationTest {

    @Autowired
    Configuration configuration;

    @Test
    void test() {
        assertNotNull(this.configuration);
        assertEquals("octo-spy-test", this.configuration.getProject());
        assertEquals("test", this.configuration.getVersion());
        assertEquals("test-env", this.configuration.getEnvironment());
        assertEquals("InternalTest", this.configuration.getClient());
        assertEquals(10, this.configuration.getDefaultApiLimit());
        assertEquals(100, this.configuration.getMaximumApiLimit());
    }
}
