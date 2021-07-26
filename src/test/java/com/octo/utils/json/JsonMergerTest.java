package com.octo.utils.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonMergerTest {

    @Test
    public void testApplyAll() {
        final JsonMerger merger = new JsonMerger();
        ObjectNode expected, source1, source2;

        expected = JsonNodeFactory.instance.objectNode();
        source1 = JsonNodeFactory.instance.objectNode();
        assertEquals(expected, merger.applyAll(source1));

        source2 = JsonNodeFactory.instance.objectNode();
        assertEquals(expected, merger.applyAll(source1, source2));

        expected = JsonNodeFactory.instance.objectNode();
        expected.put("a", "test a");
        expected.put("b", "test");
        expected.set("c", JsonNodeFactory.instance.objectNode());
        expected.put("d", "test d");
        expected.put("e", "test e");

        source1 = JsonNodeFactory.instance.objectNode();
        source1.put("a", "test a");
        source1.put("b", "test b");
        source1.put("c", "test c");
        source1.put("d", "test d");

        source2 = JsonNodeFactory.instance.objectNode();
        source1.put("b", "test");
        source1.set("c", JsonNodeFactory.instance.objectNode());
        source1.put("e", "test e");
        assertEquals(expected, merger.applyAll(source1, source2));

        assertEquals(JsonNodeFactory.instance.objectNode(), merger.applyAll(null, null));
        assertEquals(JsonNodeFactory.instance.objectNode(), merger.apply(null));
        assertEquals(JsonNodeFactory.instance.objectNode(), merger.apply(new JsonNode[] { null }));
    }

}
