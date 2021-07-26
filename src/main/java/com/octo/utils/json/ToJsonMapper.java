package com.octo.utils.json;

import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Convert Object to JSON String.
 *
 * @author vmoittie
 *
 * @param <T>
 *            Object to convert.
 */
public class ToJsonMapper<T> implements Function<T, String> {

    /**
     * Json mapper.
     */
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Default constructor, include null value.
     */
    public ToJsonMapper() {
        this(true);
    }

    /**
     * Default constructor.
     *
     * @param includeNull
     *            Indicate if you want to include null field in output String.
     */
    public ToJsonMapper(final boolean includeNull) {
        if (!includeNull) {
            this.mapper.setSerializationInclusion(Include.NON_NULL);
        }
    }

    @Override
    public final String apply(final T object) {
        try {
            return this.mapper.writeValueAsString(object);
        } catch (final JsonProcessingException e) {
            return null;
        }
    }

    /**
     * Set mapper with specific option.
     *
     * @param mapper
     *            Specific mapper.
     */
    public void setMapper(final ObjectMapper mapper) {
        this.mapper = mapper;
    }

}
