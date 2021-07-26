package com.octo.utils.json;

import java.util.List;
import java.util.function.Function;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.octo.model.error.ErrorType;
import com.octo.model.error.GlobalException;

/**
 * Convert JSONNode to object or list of objects.
 *
 * @param <T>
 *            Type of the object to return.
 *
 * @author Vincent Moittié
 *
 */
public class JsonNodeToObjectMapper<T> implements Function<JsonNode, T> {

    /**
     * Json mapper.
     */
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Type of the class to map.
     */
    private Class<T> type;

    /**
     * True if needs to instantiate a list.
     */
    private boolean isList;

    /**
     * Constructor. This constructor set the value of isList to false, by default.
     *
     * @param type
     *            The type of the class to map.
     */
    public JsonNodeToObjectMapper(final Class<T> type) {
        this.type = type;
        this.isList = false;
    }

    /**
     * Constructor.
     *
     * @param isList
     *            True if the type needs to be a lit.
     * @param type
     *            The type of the class to map.
     */
    public JsonNodeToObjectMapper(final boolean isList, final Class<T> type) {
        this.isList = isList;
        this.type = type;
    }

    @Override
    public final T apply(final JsonNode jsonNode) {
        try {
            if (!this.isList) {
                return this.mapper.reader().forType(this.type).readValue(jsonNode.toString());
            }
            final CollectionType javaType = this.mapper.getTypeFactory().constructCollectionType(List.class, this.type);
            return this.mapper.readValue(jsonNode.toString(), javaType);
        } catch (final JsonProcessingException e) {
            throw new GlobalException(e, ErrorType.INTERNAL_ERROR, "json", jsonNode.toString());
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

    /**
     * Set the type of the class to map.
     *
     * @param type
     *            The type to set.
     */
    public void setType(final Class<T> type) {
        this.type = type;
    }

    /**
     * Set the isList value. If true, the JSON will be mapped to a list of T.
     *
     * @param isList
     *            The value to set.
     */
    public void setIsList(final boolean isList) {
        this.isList = isList;
    }
}
