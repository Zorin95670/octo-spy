package com.octo.utils.json;

import java.util.function.Consumer;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Receive an array with 2 elements, the first element is the name of JSON
 * attribute and the second is the value.
 *
 * @author Vincent Moittié
 *
 */
public class ObjectArrayToJsonConsumer implements Consumer<Object[]> {

    /**
     * Json to construct.
     */
    private final ObjectNode json;
    /**
     * Default json attribute name.
     */
    private final String defaultName;

    /**
     * Set "null" as default name for json attribute.
     *
     * @param json
     *            JSON to construct.
     */
    public ObjectArrayToJsonConsumer(final ObjectNode json) {
        this(json, "null");
    }

    /**
     * Constructor.
     *
     * @param json
     *            JSON to construct.
     * @param defaultName
     *            Default json attribute name.
     */
    public ObjectArrayToJsonConsumer(final ObjectNode json, final String defaultName) {
        this.json = json;
        this.defaultName = defaultName;
    }

    @Override
    public final void accept(final Object[] array) {
        String name = this.defaultName;

        if (array[0] != null) {
            name = array[0].toString();
        }

        this.json.put(name, Integer.parseInt(array[1].toString()));
    }

}
