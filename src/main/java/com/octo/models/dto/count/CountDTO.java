package com.octo.models.dto.count;

import javax.ws.rs.QueryParam;

import com.octo.utils.predicate.filter.QueryFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Count DTO.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountDTO extends QueryFilter {
    /**
     * Field of model to count.
     */
    @QueryParam("field")
    private String field;
    /**
     * Selected value restrict count.
     */
    @QueryParam("value")
    private String value;

    /**
     * Get field of model to count.
     *
     * @return Name of field.
     */
    public String getField() {
        return this.field;
    }

    /**
     * Set field of model to count.
     *
     * @param field
     *            Name of field.
     */
    public void setField(final String field) {
        this.field = field;
    }

    /**
     * Get selected value restrict count.
     *
     * @return Value.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Set selected value restrict count.
     *
     * @param value
     *            Value.
     */
    public void setValue(final String value) {
        this.value = value;
    }

}
