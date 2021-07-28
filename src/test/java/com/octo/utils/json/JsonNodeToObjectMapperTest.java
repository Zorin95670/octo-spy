package com.octo.utils.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.octo.helpers.TestDTO;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;

class JsonNodeToObjectMapperTest {

    @Test
    void testSuccessMapperWithSimpleObject() throws JsonMappingException, JsonProcessingException {
        GlobalException exception = null;
        TestDTO result = null;
        try {
            final JsonNodeToObjectMapper<TestDTO> mapperTest = new JsonNodeToObjectMapper<>(TestDTO.class);
            final JsonNode testNode = new ObjectMapper().readTree("{\"name\":\"testName\",\"value\":\"testValue\"}");
            result = mapperTest.apply(testNode);
        } catch (final GlobalException e) {
            exception = e;
        }

        final TestDTO expected = new TestDTO("testName", "testValue");
        assertNull(exception);
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    void testSuccessMapperWithComplexeObject() throws JsonMappingException, JsonProcessingException {

        final JsonNodeToObjectMapper<List<TestDTO>> mapperTest = new JsonNodeToObjectMapper(true, TestDTO.class);
        final JsonNode testNode = new ObjectMapper().readTree("[{\"name\":\"testName\", \"value\":\"testValue\"}]");

        GlobalException exception = null;
        List<TestDTO> result = null;
        try {
            result = mapperTest.apply(testNode);
        } catch (final GlobalException e) {
            exception = e;
        }

        final List<TestDTO> expectedList = new ArrayList<>();
        final TestDTO expected = new TestDTO("testName", "testValue");
        expectedList.add(expected);

        assertNull(exception);
        assertEquals(expectedList.get(0).getName(), result.get(0).getName());
        assertEquals(expectedList.get(0).getValue(), result.get(0).getValue());

    }

    @Test
    void testFailure() throws JsonMappingException, JsonProcessingException {
        GlobalException exception = null;
        try {

            final JsonNodeToObjectMapper<List<TestDTO>> mapperTest = new JsonNodeToObjectMapper(TestDTO.class);
            final JsonNode testNode = new ObjectMapper().readTree("[{\"name\":\"testName\", \"value\":\"testValue\"}]");
            mapperTest.apply(testNode);
        } catch (final GlobalException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertEquals(ErrorType.INTERNAL_ERROR.getMessage(), exception.getMessage());
        assertEquals(ErrorType.INTERNAL_ERROR.getStatus(), exception.getStatus());
        assertEquals("[{\"name\":\"testName\",\"value\":\"testValue\"}]", exception.getError().getValue());
        assertEquals("json", exception.getError().getField());
    }

    @Test
    void testSetterIsList() throws JsonMappingException, JsonProcessingException {

        final JsonNodeToObjectMapper<List<TestDTO>> mapperTest = new JsonNodeToObjectMapper(TestDTO.class);
        mapperTest.setIsList(true);
        final JsonNode testNode = new ObjectMapper().readTree("[{\"name\":\"testName\", \"value\":\"testValue\"}]");

        GlobalException exception = null;
        List<TestDTO> result = null;
        try {
            result = mapperTest.apply(testNode);
        } catch (final GlobalException e) {
            exception = e;
        }

        assertNull(exception);
        assertNotNull(result);
        assertEquals("testName", result.get(0).getName());
        assertEquals("testValue", result.get(0).getValue());
    }

    @Test
    void testSetterType() throws JsonMappingException, JsonProcessingException {
        final JsonNodeToObjectMapper<TestDTO> mapperTest = new JsonNodeToObjectMapper(String.class);
        mapperTest.setType(TestDTO.class);
        final JsonNode testNode = new ObjectMapper().readTree("{\"name\":\"testName\", \"value\":\"testValue\"}");

        GlobalException exception = null;
        TestDTO result = null;
        try {
            result = mapperTest.apply(testNode);
        } catch (final GlobalException e) {
            exception = e;
        }

        assertNull(exception);
        assertNotNull(result);
        assertEquals("testName", result.getName());
        assertEquals("testValue", result.getValue());
    }

    @Test
    void testSetterMapper() throws JsonMappingException, JsonProcessingException {
        final JsonNodeToObjectMapper<TestDTO> mapperTest = new JsonNodeToObjectMapper(TestDTO.class);
        mapperTest.setMapper(new ObjectMapper());
        final JsonNode testNode = new ObjectMapper().readTree("{\"name\":\"testName\", \"value\":\"testValue\"}");

        GlobalException exception = null;
        TestDTO result = null;
        try {
            result = mapperTest.apply(testNode);
        } catch (final GlobalException e) {
            exception = e;
        }

        assertNull(exception);
        assertNotNull(result);
        assertEquals("testName", result.getName());
        assertEquals("testValue", result.getValue());
    }
}
