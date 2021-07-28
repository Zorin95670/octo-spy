package com.octo.helpers;

import com.octo.model.common.DefaultDTO;
import com.octo.utils.predicate.filter.FilterType;
import com.octo.utils.predicate.filter.IPredicateFilter;
import com.octo.utils.predicate.filter.TextPredicateFilter;

public class TestDTO extends DefaultDTO {
    private String name;
    private Object value;
    @FilterType(type = FilterType.Type.TEXT)
    private String test;

    public TestDTO() {
        super();
    }

    public TestDTO(final String name, final Object value) {
        super();
        this.name = name;
        this.value = value;
        this.test = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(final Object value) {
        this.value = value;
    }

    @Override
    public boolean isSpecificFilter(String name) {
        return "test".equals(name);
    }

    @Override
    public IPredicateFilter getSpecificFilter(String name, String value) {
        return new TextPredicateFilter(name, value);
    }
}