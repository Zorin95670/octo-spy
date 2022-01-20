package com.octo.utils.bean;

import com.octo.model.deployment.DeploymentDTO;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Tag("unit")
class NullAwareBeanUtilsBeanTest {

    @Test
    void testCopyProperty() throws IllegalAccessException, InvocationTargetException {
        NullAwareBeanUtilsBean beanUtil = new NullAwareBeanUtilsBean();
        DeploymentDTO dto = new DeploymentDTO();

        assertNull(dto.getVersion());

        beanUtil.copyProperty(dto, "version", "test");
        assertEquals("test", dto.getVersion());

        beanUtil.copyProperty(dto, "version", null);
        assertEquals("test", dto.getVersion());

        beanUtil = new NullAwareBeanUtilsBean("version");
        dto = new DeploymentDTO();
        beanUtil.copyProperty(dto, "version", "test");
        assertNull(dto.getVersion());
    }
}
