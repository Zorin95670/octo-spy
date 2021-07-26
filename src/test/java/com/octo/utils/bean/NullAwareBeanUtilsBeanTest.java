package com.octo.utils.bean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

import com.octo.helpers.EntityTestSearch;

public class NullAwareBeanUtilsBeanTest {

    @Test
    public void testCopyProperty() throws IllegalAccessException, InvocationTargetException {
        NullAwareBeanUtilsBean beanUtil = new NullAwareBeanUtilsBean();
        EntityTestSearch dto = new EntityTestSearch();

        assertNull(dto.getName());

        beanUtil.copyProperty(dto, "name", "test");
        assertEquals("test", dto.getName());

        beanUtil.copyProperty(dto, "name", null);
        assertEquals("test", dto.getName());

        beanUtil = new NullAwareBeanUtilsBean("name");
        dto = new EntityTestSearch();
        beanUtil.copyProperty(dto, "name", "test");
        assertNull(dto.getName());
    }
}
