package com.octo.utils.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ObjectArrayToJsonConsumerTest {

    @Test
    public void acceptTest() {
        ObjectNode json = null;
        Object[] array = null;
        ObjectArrayToJsonConsumer consumer = null;

        // Test default null name
        json = JsonNodeFactory.instance.objectNode();
        consumer = new ObjectArrayToJsonConsumer(json);
        array = new Object[] { null, "0" };
        consumer.accept(array);

        assertEquals("{\"null\":0}", json.toString());

        // Test default name as "test"
        json = JsonNodeFactory.instance.objectNode();
        consumer = new ObjectArrayToJsonConsumer(json, "test");
        array = new Object[] { null, "0" };
        consumer.accept(array);

        assertEquals("{\"test\":0}", json.toString());

        // Test good value
        json = JsonNodeFactory.instance.objectNode();
        consumer = new ObjectArrayToJsonConsumer(json, "test");
        array = new Object[] { "name", "0" };
        consumer.accept(array);

        assertEquals("{\"name\":0}", json.toString());
    }
}
