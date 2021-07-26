package com.octo.utils.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ToJsonMapperTest {

    class TestObject {
        public String name;
        public String value;
    }

    @Test
    public void testNullInclude() {
        final ToJsonMapper<TestObject> mapper = new ToJsonMapper<>();
        final TestObject test = new TestObject();
        assertEquals("{\"name\":null,\"value\":null}", mapper.apply(test));

        test.name = "testName";
        test.value = "testValue";
        assertEquals("{\"name\":\"testName\",\"value\":\"testValue\"}", mapper.apply(test));
    }

    @Test
    public void testNullNotInclude() {
        final ToJsonMapper<TestObject> mapper = new ToJsonMapper<>(false);
        final TestObject test = new TestObject();
        assertEquals("{}", mapper.apply(test));

        test.name = "testName";
        assertEquals("{\"name\":\"testName\"}", mapper.apply(test));
    }

    @Test
    public void testNullInput() {
        final ToJsonMapper<TestObject> mapper = new ToJsonMapper<>(false);
        assertEquals("null", mapper.apply(null));
    }

    @Test
    public void testSetMapper() {
        final ToJsonMapper<TestObject> mapper = new ToJsonMapper<>();
        final ObjectMapper mapperJson = new ObjectMapper();
        mapperJson.setSerializationInclusion(Include.NON_NULL);

        mapper.setMapper(mapperJson);

        final TestObject test = new TestObject();
        test.name = "testName";
        assertEquals("{\"name\":\"testName\"}", mapper.apply(test));

        class Bad {
            @Override
            public String toString() { // this make a JsonProccessinqException.
                return this.getClass().getName();
            }
        }

        assertNull(new ToJsonMapper<>().apply(new Bad()));

    }
}
