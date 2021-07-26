package com.octo.utils.json;

import java.util.Arrays;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Json merger.
 *
 * @author Vincent Moittié
 *
 */
public class JsonMerger implements Function<JsonNode[], JsonNode> {

    @Override
    public final JsonNode apply(final JsonNode[] sources) {
        final ObjectNode json = JsonNodeFactory.instance.objectNode();
        if (sources == null) {
            return json;
        }

        Arrays.stream(sources).filter(Objects::nonNull).forEach(source -> {
            final Spliterator<String> spliterator = Spliterators.spliteratorUnknownSize(source.fieldNames(), 0);
            StreamSupport.stream(spliterator, false).forEach(key -> json.set(key, source.get(key)));
        });

        return json;
    }

    /**
     * Applies this function to the given argument.
     *
     * @param sources
     *            Sources to merge
     * @return Merged json.
     */
    public final JsonNode applyAll(final JsonNode... sources) {
        return this.apply(sources);
    }

}
