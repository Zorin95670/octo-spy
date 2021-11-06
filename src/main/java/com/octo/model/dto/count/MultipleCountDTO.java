package com.octo.model.dto.count;

import java.util.List;

import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.octo.utils.predicate.filter.QueryFilter;

/**
 * Multiple count DTO.
 *
 * @author Vincent Moittié
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MultipleCountDTO extends QueryFilter {
    /**
     * Fields of model to count.
     */
    @QueryParam("fields")
    private List<String> fields;

    /**
     * Get fields of model to count.
     *
     * @return Name of field.
     */
    public List<String> getFields() {
        return this.fields;
    }

    /**
     * Set fields of model to count.
     *
     * @param fields
     *            Names of field.
     */
    public void setFields(final List<String> fields) {
        this.fields = fields;
    }
}
