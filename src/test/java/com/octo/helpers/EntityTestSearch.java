package com.octo.helpers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.octo.utils.predicate.filter.FilterType;
import com.octo.utils.predicate.filter.QueryFilter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EntityTestSearch extends QueryFilter {
    @FilterType(type = FilterType.Type.NUMBER)
    private String id;
    @FilterType(type = FilterType.Type.DATE)
    private String updateDate;
    @FilterType(type = FilterType.Type.BOOLEAN)
    private String alive;
    @FilterType(type = FilterType.Type.ARRAY)
    private String array;
    @FilterType(type = FilterType.Type.TEXT)
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getAlive() {
        return alive;
    }

    public void setAlive(String alive) {
        this.alive = alive;
    }

    public String getArray() {
        return array;
    }

    public void setArray(String array) {
        this.array = array;
    }
}
